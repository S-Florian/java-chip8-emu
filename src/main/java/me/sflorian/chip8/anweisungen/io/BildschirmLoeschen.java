package me.sflorian.chip8.anweisungen.io;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.Anweisung;

public class BildschirmLoeschen extends Anweisung {
    @Override
    public void ausfuehren(Prozessor prozessor) {
        // TODO: Bildschirm löschen
    }

    @Override
    public short enkodieren() {
        return 0x00E0;
    }

    @Override
    public String alsAssembly() {
        return "CLS";
    }
}
