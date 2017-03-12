package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class VerzweigungRegister extends Anweisung {
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
        byte a = p.regVGeben(registerA);
        byte b = p.regVGeben(registerB);

        switch (bedingung) {
            case GLEICH:
                if (a == b) p.weiter();
                break;

            case UNGLEICH:
                if (a != b) p.weiter();
                break;

            default:
                break;
        }
    }

    @Override
    public short enkodieren() {
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
        return String.format("%s V%1X, V%1X", bedingung.nameGeben(), registerA, registerB);
    }
}
