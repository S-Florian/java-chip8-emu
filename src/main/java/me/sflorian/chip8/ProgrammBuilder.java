package me.sflorian.chip8;

import me.sflorian.chip8.befehle.Befehl;

import java.util.LinkedList;
import java.util.List;

public class ProgrammBuilder {
    private final List<Befehl> befehle = new LinkedList<>();
    private int befehlAnzahl = 0;

    public ProgrammBuilder mit(Befehl befehl) {
        befehle.add(befehl);
        befehlAnzahl++;
        return this;
    }

    public byte[] erstellen() {
        byte[] programm = new byte[befehlAnzahl * 2];

        int i = 0;

        for (Befehl a : befehle) {
            short op = a.enkodieren();
            programm[i] = (byte)((op >> 8) & 0xFF);
            programm[i + 1] = (byte)(op & 0xFF);
            i += 2;
        }

        return programm;
    }
}
