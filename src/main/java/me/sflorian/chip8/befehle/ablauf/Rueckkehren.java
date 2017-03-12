package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;

public class Rueckkehren extends Befehl {
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
