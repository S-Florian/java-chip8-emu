package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;

public class Stopp extends Befehl {
    @Override
    public void ausfuehren(Prozessor prozessor) {

    }

    @Override
    public short enkodieren() {
        return 0x0000;
    }

    @Override
    public String alsAssembly() {
        return "STOP";
    }
}
