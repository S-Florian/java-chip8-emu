package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class RegisterSetzen extends Anweisung {
    private int registerNummer;
    private byte wert;

    public RegisterSetzen(int registerNummer, byte wert) {
        if (registerNummer < 0 || registerNummer >= Prozessor.CHIP8_ANZAHL_V_REGISTER) {
            throw new IllegalArgumentException("registerNummer muss zw. 0x0 und 0xF liegen (inklusiv)!");
        }

        this.registerNummer = registerNummer;
        this.wert = wert;
    }

    @Override
    public void ausfuehren(Prozessor prozessor) {
        prozessor.regVSetzen(registerNummer, wert);
    }

    @Override
    public short enkodieren() {
        return (short) (0x6000 | (registerNummer << 8) | wert);
    }
}
