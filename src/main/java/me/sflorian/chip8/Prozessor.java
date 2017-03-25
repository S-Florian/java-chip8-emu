package me.sflorian.chip8;

import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.ablauf.Stopp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Enthält Register, Arbeitsspeicher und CPU.
 */
public class Prozessor implements EingabeListener, Closeable {
    public static final int CHIP8_AUFRUFSTAPEL_GROESSE = 16;
    public static final int CHIP8_ANZAHL_V_REGISTER = 16;

    private static final int MS_PRO_ZYKLUS = 2;

    private Arbeitsspeicher mem;
    private short[] aufrufstapel = new short[CHIP8_AUFRUFSTAPEL_GROESSE];

    // CPU-Register
    private byte[] V = new byte[CHIP8_ANZAHL_V_REGISTER]; // Die 16 allgemeinen Register, also V0-VF.
    private short PC = 0; // Der Program Counter, also die Addresse des momentan ausgeführten Programmbefehls im Arbeitsspeicher.
    private byte SP = 0; // Der Stack Pointer, die momentane Größe des Aufrufstapels.
    private short I = 0; // Addressierungs-Register
    private byte ST = 0; // Sound-Timer
    private byte DT = 0; // Delay-Timer

    // Kommunikation mit der Außenwelt
    private Display display;
    private Lautsprecher lautsprecher;

    private int tastendruckErwartetRegister = -1;

    public Prozessor(Arbeitsspeicher arbeitsspeicher, Display display, Lautsprecher lautsprecher) {
        if (arbeitsspeicher == null) throw new IllegalArgumentException("arbeitsspeicher darf nicht null sein!");
        mem = arbeitsspeicher;

        this.display = display;
        this.lautsprecher = lautsprecher;

        if (display != null) {
            Eingabe eingabe = display.eingabeGeben();

            if (eingabe != null)
                eingabe.eingabeListenerHinzufuegen(this);
        }

        zahlenLaden();
    }

    public Prozessor(Arbeitsspeicher arbeitsspeicher, Display display) {
        this(arbeitsspeicher, display, null);
    }

    public Prozessor(Arbeitsspeicher arbeitsspeicher) {
        this(arbeitsspeicher, null, null);
    }

    public static boolean istGueltigerRegisterIndex(int i) {
        return i >= 0 && i < Prozessor.CHIP8_ANZAHL_V_REGISTER;
    }

    private void zahlenLaden() {
        byte[] bytes = new byte[Zahlen.ZAHLEN_BLOCK.length];

        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)Zahlen.ZAHLEN_BLOCK[i];
        }

        mem.multiPoke(0x00, bytes);
    }

    public void programmLaden(byte[] programm) {
        programmLaden(programm, true);
    }

    public void programmLaden(byte[] programm, boolean pcSetzen) {
        mem.programmLaden(programm);

        if (pcSetzen)
            PC = Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION;
    }

    public void aufTasteWarten(int register) {
        if (!istGueltigerRegisterIndex(register))
            return;

        tastendruckErwartetRegister = register;
    }

    private long letzterZyklus = System.currentTimeMillis();
    private long dtTimer = 0;

    /**
     * Führt einen Befehlszyklus aus, d.h. Befehl holen, dekodieren, ausführen.
     */
    public boolean zyklus() {
        Befehl befehl = naechstenBefehlGeben();

        if (befehl == null || befehl instanceof Stopp)
            return false;

        long dt = System.currentTimeMillis() - letzterZyklus;
        dtTimer += dt;

        if (dtTimer > 1000 / 60) {
            dtTimer = 0;

            DT = (byte) Math.max(0, ((int) DT & 0xFF) - 1);

            int st = (int) ST & 0xFF;

            if (st > 0) {
                ST = (byte) (st - 1);

                if (lautsprecher != null && lautsprecher.kannTonAbspielen() && !lautsprecher.wirdAbgespielt())
                    lautsprecher.tonAbspielen();
            } else if (st <= 0) {
                ST = 0;

                if (lautsprecher != null && lautsprecher.wirdAbgespielt())
                    lautsprecher.tonStoppen();
            }
        }

        letzterZyklus = System.currentTimeMillis();

        befehl.ausfuehren(this);
        return true;
    }

    public void programmAusfuehren() {
        while (true) {
            long startZeit = System.currentTimeMillis();

            if (!istGueltigerRegisterIndex(tastendruckErwartetRegister)) {
                if (!zyklus())
                    return;

                weiter();
            }

            try {
                Thread.sleep(Math.max(0, startZeit + MS_PRO_ZYKLUS - System.currentTimeMillis()));
            } catch (InterruptedException ignored) {}
        }
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
     * Erhöht PC, sodass die nächste Befehl ausgeführt wird.
     */
    public void weiter() {
        PC += 2; // Eine Befehl ist 16-bit lang, also 2 bytes.
    }

    public void springenZu(short addresse) {
        PC = addresse;
    }

    @Override
    public void tasteGedrueckt(int taste) {
        if (istGueltigerRegisterIndex(tastendruckErwartetRegister)) {
            regVSetzen(tastendruckErwartetRegister, (byte) taste);
            tastendruckErwartetRegister = -1;
        }
    }

    // Geben/Setzen Methoden

    public short naechstenOpCodeGeben() {
        byte[] opBytes = mem.multiPeek(PC, 2);
        return (short) (((opBytes[0] & 0xFF) << 8) | (opBytes[1] & 0xFF));
    }

    public Befehl naechstenBefehlGeben() {
        return Befehl.dekodieren(naechstenOpCodeGeben());
    }

    public Arbeitsspeicher arbeitsspeicherGeben() {
        return mem;
    }

    public Display displayGeben() {
        return display;
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

    @Override
    public void close() {
        mem = null;

        if (display != null) {
            Eingabe eingabe = display.eingabeGeben();

            if (eingabe != null)
                eingabe.eingabeListenerEntfernen(this);
        }
    }
}
