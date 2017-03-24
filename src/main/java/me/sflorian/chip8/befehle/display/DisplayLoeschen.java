package me.sflorian.chip8.befehle.display;

import me.sflorian.chip8.Display;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;

public class DisplayLoeschen extends Befehl {
    @Override
    public void ausfuehren(Prozessor prozessor) {
        Display d = prozessor.displayGeben();

        if (d != null) {
            d.loeschen();
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
