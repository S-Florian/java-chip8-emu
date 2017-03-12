package me.sflorian.chip8;

import me.sflorian.chip8.anweisungen.Anweisung;
import me.sflorian.chip8.anweisungen.Stopp;

/**
 * Enthält Register, Arbeitsspeicher und CPU.
 * Wird hauptsächlich durch CPU mutiert.
 */
public class Prozessor {
    public static final int CHIP8_AUFRUFSTAPEL_GROESSE = 16;
    public static final int CHIP8_ANZAHL_V_REGISTER = 16;

    private Arbeitsspeicher mem;
    private short[] aufrufstapel = new short[CHIP8_AUFRUFSTAPEL_GROESSE];

    // CPU-Register
    private byte[] V = new byte[CHIP8_ANZAHL_V_REGISTER]; // Die 16 allgemeinen Register, also V0-VF.
    private short PC = 0; // Der Program Counter, also die Addresse der momentan ausgeführten Programmanweisung im Arbeitsspeicher.
    private byte SP = 0; // Der Stack Pointer, die momentane Größe des Aufrufstapels.
    private short I = 0; // Addressierungs-Register
    private byte ST = 0; // Sound-Timer
    private byte DT = 0; // Delay-Timer

    public Prozessor(Arbeitsspeicher arbeitsspeicher) {
        if (arbeitsspeicher == null) throw new IllegalArgumentException("arbeitsspeicher darf nicht null sein!");
        mem = arbeitsspeicher;
    }

    public static boolean istGueltigerRegisterIndex(int i) {
        return i >= 0 && i < Prozessor.CHIP8_ANZAHL_V_REGISTER;
    }

    public void programmLaden(byte[] programm) {
        programmLaden(programm, true);
    }

    public void programmLaden(byte[] programm, boolean pcSetzen) {
        mem.programmLaden(programm);

        if (pcSetzen)
            PC = Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION;
    }

    /**
     * Führt einen Befehlszyklus aus, d.h. Anweisung holen, dekodieren, ausführen.
     */
    public boolean zyklus() {
        Anweisung anweisung = naechsteAnweisungGeben();

        if (anweisung == null || anweisung instanceof Stopp)
            return false;

        anweisung.ausfuehren(this);
        return true;
    }

    public void programmAusfuehren() {
        while (zyklus()) weiter();
    }

    public boolean aufrufen(short addresse) {
        if (SP >= aufrufstapel.length - 1)
            return false; // Aufrufstapel ist voll!!

        if (SP < 0)
            SP = 0; // Sollte nicht geschehen, aber nur um sicherzustellen...

        aufrufstapel[SP++] = PC;
        springenZu((short) (addresse - 2));
        return true;
    }

    public void zurueckkehren() {
        if (SP - 1 < 0)
            return; // Wir können nirgendwo zurückkehren.

        if (SP - 1 >= aufrufstapel.length)
            return; // Kann passieren, wenn SP einen korrupten Wert hat.

        short addresse = aufrufstapel[SP - 1];
        springenZu(addresse);
        SP--;
    }

    public void registerSpeichern(int anfang, int ende, short addresse) {
        int laenge = ende - anfang + 1;

        if (mem.istGueltigeAddresse(addresse) && mem.istGueltigeAddresse(addresse + laenge))
            System.arraycopy(V, anfang, mem.speicher, addresse, laenge);
    }

    public void registerLaden(int anfang, int ende, short addresse) {
        int laenge = ende - anfang + 1;

        if (mem.istGueltigeAddresse(addresse) && mem.istGueltigeAddresse(addresse + laenge))
            System.arraycopy(mem.speicher, addresse, V, anfang, laenge);
    }

    /**
     * Erhöht PC, sodass die nächste Anweisung ausgeführt wird.
     */
    public void weiter() {
        PC += 2; // Eine Anweisung ist 16-bit lang, also 2 bytes.
    }

    public void springenZu(short addresse) {
        PC = addresse;
    }

    // Geben/Setzen Methoden

    public short naechstenOpCodeGeben() {
        byte[] opBytes = mem.multiPeek(PC, 2);
        short op = (short) (((opBytes[0] & 0xFF) << 8) | (opBytes[1] & 0xFF));
        return op;
    }

    public Anweisung naechsteAnweisungGeben() {
        return Anweisung.dekodieren(naechstenOpCodeGeben());
    }

    public Arbeitsspeicher arbeitsspeicherGeben() {
        return mem;
    }

    public byte regVGeben(int i) {
        return i < V.length && i >= 0 ? V[i] : 0;
    }

    public short regPCGeben() {
        return PC;
    }

    public short regSPGeben() {
        return SP;
    }

    public short regIGeben() {
        return I;
    }

    public byte regSTGeben() {
        return ST;
    }

    public byte regDTGeben() {
        return DT;
    }

    public void regVSetzen(int i, byte j) {
        if (i < V.length && i >= 0) V[i] = j;
    }

    public void regPCSetzen(byte i) {
        PC = i;
    }

    public void regSPSetzen(byte i) {
        SP = i;
    }

    public void regISetzen(short i) {
        I = i;
    }

    public void regSTSetzen(byte i) {
        ST = i;
    }

    public void regDTSetzen(byte i) {
        DT = i;
    }
}
