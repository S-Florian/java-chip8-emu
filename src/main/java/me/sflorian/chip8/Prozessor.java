package me.sflorian.chip8;

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

    public void programmLaden(byte[] programm) {
        mem.programmLaden(programm);
    }

    // Geben/Setzen Methoden

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
