package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class Sprung extends Anweisung {
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
}
