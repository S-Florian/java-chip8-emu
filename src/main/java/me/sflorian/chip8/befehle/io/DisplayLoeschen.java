package me.sflorian.chip8.befehle.io;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.schnittstellen.Display;

public class DisplayLoeschen extends Befehl {
    @Override
    public void ausfuehren(Prozessor prozessor) {
        Display d = prozessor.displayGeben();

        if (d != null) {
            d.loeschen();
            d.anzeigen();
        }
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
