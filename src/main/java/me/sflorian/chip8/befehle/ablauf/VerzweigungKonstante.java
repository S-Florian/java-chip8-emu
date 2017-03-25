package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.Bedingung;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;
import me.sflorian.chip8.befehle.helfer.Verzweigung;

public class VerzweigungKonstante extends Befehl {
    private final int register;
    private final byte wert;
    private final Bedingung bedingung;

    public VerzweigungKonstante(int register, byte wert, Bedingung bedingung) {
        if (!Prozessor.istGueltigerRegisterIndex(register))
            throw new IllegalArgumentException("register muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.register = register;
        this.wert = wert;
        this.bedingung = bedingung;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        if (bedingung == null) return;

        byte a = p.regVGeben(register);
        byte b = wert;

        Verzweigung.verarbeiten(p, a, b, bedingung);
    }

    @Override
    public short enkodieren() {
        if (bedingung == null)
            return 0x0000;

        int i;

        switch (bedingung) {
            case GLEICH:
                i = 0x3;
                break;

            case UNGLEICH:
                i = 0x4;
                break;

            default:
                return 0;
        }

        return EnkodierungsHelfer._XNN(i, (byte)register, wert);
    }

    @Override
    public String alsAssembly() {
        String name = bedingung != null ? bedingung.nameGeben() : "SKIP";
        return String.format("%s V%1X, %d", name, register, (int)wert & 0xFF);
    }
}
