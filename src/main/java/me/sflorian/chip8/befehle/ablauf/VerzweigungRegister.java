package me.sflorian.chip8.befehle.ablauf;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.Bedingung;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;
import me.sflorian.chip8.befehle.helfer.Verzweigung;

public class VerzweigungRegister extends Befehl {
    private final int registerA, registerB;
    private final Bedingung bedingung;

    public VerzweigungRegister(int registerA, int registerB, Bedingung bedingung) {
        if (!Prozessor.istGueltigerRegisterIndex(registerA))
            throw new IllegalArgumentException("registerA muss zw. 0x0 und 0xF liegen (inklusiv)!");

        if (!Prozessor.istGueltigerRegisterIndex(registerB))
            throw new IllegalArgumentException("registerB muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.registerA = registerA;
        this.registerB = registerB;
        this.bedingung = bedingung;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        if (bedingung == null) return;

        byte a = p.regVGeben(registerA);
        byte b = p.regVGeben(registerB);

        Verzweigung.verarbeiten(p, a, b, bedingung);
    }

    @Override
    public short enkodieren() {
        if (bedingung == null)
            return 0x0000;

        int i;

        switch (bedingung) {
            case GLEICH:
                i = 0x5;
                break;

            case UNGLEICH:
                i = 0x9;
                break;

            default:
                return 0;
        }

        return EnkodierungsHelfer._XY_(i, (byte)registerA, (byte)registerB, 0);
    }

    @Override
    public String alsAssembly() {
        String name = bedingung != null ? bedingung.nameGeben() : "SKIPR";
        return String.format("%s V%1X, V%1X", name, registerA, registerB);
    }
}
