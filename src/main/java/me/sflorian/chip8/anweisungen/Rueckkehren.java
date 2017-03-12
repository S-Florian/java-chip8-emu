package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

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
