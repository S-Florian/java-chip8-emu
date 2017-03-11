package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class RegisterSetzen extends Anweisung {
    private final int registerNummer;
    private final byte wert;

    public RegisterSetzen(int registerNummer, byte wert) {
        if (!Prozessor.istGueltigerRegisterIndex(registerNummer))
            throw new IllegalArgumentException("registerNummer muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.registerNummer = registerNummer;
        this.wert = wert;
    }

    @Override
    public void ausfuehren(Prozessor prozessor) {
        prozessor.regVSetzen(registerNummer, wert);
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XNN(0x6, (byte)registerNummer, wert);
    }
}
