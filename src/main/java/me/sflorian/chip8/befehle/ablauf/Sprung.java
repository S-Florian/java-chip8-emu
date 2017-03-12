package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

public class Sprung extends Befehl {
    private final short addresse;
    private final boolean verschobenUmV0;

    public Sprung(short addresse) {
        this(addresse, false);
    }

    public Sprung(short addresse, boolean verschobenUmV0) {
        this.addresse = addresse;
        this.verschobenUmV0 = verschobenUmV0;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        short addr = addresse;

        if (verschobenUmV0)
            addr += p.regVGeben(0);

        p.springenZu(addr);
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._NNN(verschobenUmV0 ? 0xB : 0x1, addresse);
    }

    @Override
    public String alsAssembly() {
        if (verschobenUmV0)
            return String.format("JP V0, $%04X", addresse);
        else
            return String.format("JP $%04X", addresse);
    }
}
