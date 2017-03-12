package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

import java.util.Random;

public class Zufallszahl extends Anweisung {
    private static final Random random = new Random();

    private final int register;
    private final byte maske;

    public Zufallszahl(int register, byte maske) {
        if (!Prozessor.istGueltigerRegisterIndex(register))
            throw new IllegalArgumentException("register muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.register = register;
        this.maske = maske;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        byte[] b = new byte[1];
        random.nextBytes(b);
        p.regVSetzen(register, (byte) (b[0] & maske));
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XNN(0xC, (byte)register, maske);
    }
}
