package me.sflorian.chip8.befehle.register;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

public class AddresseSetzen extends Befehl {
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
