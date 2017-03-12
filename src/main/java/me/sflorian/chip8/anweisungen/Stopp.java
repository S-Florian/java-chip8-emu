package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class Stopp extends Anweisung {
    @Override
    public void ausfuehren(Prozessor prozessor) {

    }

    @Override
    public short enkodieren() {
        return 0x0000;
    }
}
