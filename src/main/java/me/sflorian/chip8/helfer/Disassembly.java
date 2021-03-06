package me.sflorian.chip8.helfer;

import me.sflorian.chip8.befehle.Befehl;

public class Disassembly {
    public static String generieren(byte[] programm) {
        if (programm.length % 2 != 0) return null;

        StringBuilder sb = new StringBuilder();
        String nl = System.getProperty("line.separator");

        for (int i = 0; i < programm.length; i += 2) {
            byte a = programm[i];
            byte b = programm[i + 1];
            short opcode = (short) (((a & 0xFF) << 8) | (b & 0xFF));

            Befehl befehl = Befehl.dekodieren(opcode);
            sb.append(befehl == null ? "???" : befehl.alsAssembly());
            sb.append(nl);
        }

        return sb.toString();
    }
}
