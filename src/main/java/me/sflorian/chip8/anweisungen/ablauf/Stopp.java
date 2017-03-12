package me.sflorian.chip8.anweisungen.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.Anweisung;

public class Stopp extends Anweisung {
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
