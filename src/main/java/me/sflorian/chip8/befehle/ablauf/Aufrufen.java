package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

public class Aufrufen extends Befehl {
    private final short addresse;

    public Aufrufen(short addresse) {
        this.addresse = addresse;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        p.aufrufen(addresse);
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._NNN(0x2, addresse);
    }

    @Override
    public String alsAssembly() {
        return String.format("CALL $%04X", addresse);
    }
}
