package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class VerzweigungKonstante extends Anweisung {
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
        byte a = p.regVGeben(register);
        byte b = wert;

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
}