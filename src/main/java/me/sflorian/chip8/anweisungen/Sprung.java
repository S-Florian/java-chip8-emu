package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class Sprung extends Anweisung {
    private final short addresse;

    public Sprung(short addresse) {
        this.addresse = addresse;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        p.springenZu(addresse);
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._NNN(0x1, addresse);
    }
}
