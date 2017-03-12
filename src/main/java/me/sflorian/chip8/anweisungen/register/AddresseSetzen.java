package me.sflorian.chip8.anweisungen.register;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.Anweisung;
import me.sflorian.chip8.anweisungen.helfer.EnkodierungsHelfer;

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

    @Override
    public String alsAssembly() {
        return String.format("LD I, $%04X", addresse);
    }
}
