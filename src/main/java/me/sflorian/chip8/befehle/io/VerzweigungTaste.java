package me.sflorian.chip8.befehle.io;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;
import me.sflorian.chip8.schnittstellen.Display;
import me.sflorian.chip8.schnittstellen.Eingabe;

public class VerzweigungTaste extends Befehl {
    private final int register;
    private final boolean gedrueckt;

    public VerzweigungTaste(int register, boolean gedrueckt) {
        this.register = register;
        this.gedrueckt = gedrueckt;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        Display d = p.displayGeben();

        if (d != null) {
            Eingabe e = d.eingabeGeben();

            if (e != null) {
                int t = p.regVGeben(register);

                if (e.wirdGedrueckt(t) == gedrueckt)
                    p.weiter();
            }
        }
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._X__(0xE, (byte) register, gedrueckt ? 0x9E : 0xA1);
    }

    @Override
    public String alsAssembly() {
        String name = gedrueckt ? "SKP" : "SKNP";
        return String.format("%s V%01X", name, register);
    }
}
