package me.sflorian.chip8.befehle.io;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;

public class BildschirmLoeschen extends Befehl {
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
