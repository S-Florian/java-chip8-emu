package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class BildschirmLoeschen extends Anweisung {
    @Override
    public void ausfuehren(Prozessor prozessor) {
        // TODO: Bildschirm löschen
    }

    @Override
    public short enkodieren() {
        return 0x00E0;
    }
}
