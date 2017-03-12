package me.sflorian.chip8.anweisungen.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.Anweisung;

public class Rueckkehren extends Anweisung {
    @Override
    public void ausfuehren(Prozessor p) {
        p.zurueckkehren();
    }

    @Override
    public short enkodieren() {
        return 0x00EE;
    }

    @Override
    public String alsAssembly() {
        return "RET";
    }
}
