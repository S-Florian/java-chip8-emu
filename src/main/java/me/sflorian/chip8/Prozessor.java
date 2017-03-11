package me.sflorian.chip8;

import me.sflorian.chip8.anweisungen.Anweisung;

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
    private short SP = 0; // Der Stack Pointer, die momentane Größe des Aufrufstapels.

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

        if (anweisung == null)
            return false;

        anweisung.ausfuehren(this);
        return true;
    }

    public void programmAusfuehren() {
        while (zyklus());
    }

    // Geben/Setzen Methoden

    public short naechstenOpCodeGeben() {
        byte[] opBytes = mem.multiPeek(PC, 2);
        short op = (short) ((opBytes[0] << 8) | opBytes[1]);
        PC += 2; // Eine Anweisung ist 16-bit lang, also 2 bytes.
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

    public void regVSetzen(int i, byte j) {
        if (i < V.length && i >= 0) V[i] = j;
    }

    public void regPCSetzen(byte i) {
        PC = i;
    }

    public void regSPSetzen(byte i) {
        SP = i;
    }
}
