package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class AddresseSetzen extends Anweisung {
    private final short addresse;

    public AddresseSetzen(short addresse) {
        this.addresse = addresse;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        p.regISetzen(addresse);
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._NNN(0xA, addresse);
    }
}
