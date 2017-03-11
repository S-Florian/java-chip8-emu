package me.sflorian.chip8;

public class Arbeitsspeicher {
    public static final int CHIP8_ARBEITSSPEICHER_GROESSE = 4096; // 4096 bytes = 4 KB
    public static final int CHIP8_AS_PROGRAMM_POSITION = 0x200;

    private byte[] speicher;

    public Arbeitsspeicher() {
        this(CHIP8_ARBEITSSPEICHER_GROESSE);
    }

    public Arbeitsspeicher(int groesse) {
        speicher = new byte[groesse];
    }

    public void programmLaden(byte[] programm) {
        int anfang = CHIP8_AS_PROGRAMM_POSITION;
        int ende = CHIP8_AS_PROGRAMM_POSITION + programm.length;

        if (istGueltigeAddresse(anfang) && istGueltigeAddresse(ende))
            System.arraycopy(programm, 0, speicher, anfang, programm.length);
    }

    public boolean istGueltigeAddresse(int addresse) {
        return addresse >= 0 && addresse < speicher.length;
    }

    public byte peek(int addresse) {
        if (!istGueltigeAddresse(addresse)) return 0;
        return speicher[addresse];
    }

    public void poke(int addresse, byte neuerWert) {
        if (!istGueltigeAddresse(addresse)) return;
        speicher[addresse] = neuerWert;
    }
}
