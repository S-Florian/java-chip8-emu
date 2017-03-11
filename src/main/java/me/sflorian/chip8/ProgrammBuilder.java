package me.sflorian.chip8;

import me.sflorian.chip8.anweisungen.Anweisung;

import java.util.LinkedList;
import java.util.List;

public class ProgrammBuilder {
    private final List<Anweisung> anweisungen = new LinkedList<>();
    private int anweisungsAnzahl = 0;

    public ProgrammBuilder mit(Anweisung anweisung) {
        anweisungen.add(anweisung);
        anweisungsAnzahl++;
        return this;
    }

    public byte[] erstellen() {
        byte[] programm = new byte[anweisungsAnzahl * 2];

        int i = 0;

        for (Anweisung a : anweisungen) {
            short op = a.enkodieren();
            programm[i] = (byte)((op >> 8) & 0xFF);
            programm[i + 1] = (byte)(op & 0xFF);
            i += 2;
        }

        return programm;
    }
}
