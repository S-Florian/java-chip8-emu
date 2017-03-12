package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

public class RegisterHinzufuegen extends Anweisung {
    private final int registerNummer;
    private final byte wert;

    public RegisterHinzufuegen(int registerNummer, byte wert) {
        if (!Prozessor.istGueltigerRegisterIndex(registerNummer))
            throw new IllegalArgumentException("registerNummer muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.registerNummer = registerNummer;
        this.wert = wert;
    }

    @Override
    public void ausfuehren(Prozessor prozessor) {
        prozessor.regVSetzen(registerNummer, (byte) (prozessor.regVGeben(registerNummer) + wert));
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XNN(0x7, (byte)registerNummer, wert);
    }

    @Override
    public String alsAssembly() {
        return String.format("ADD V%1X, %d", registerNummer, (int)wert & 0xFF);
    }
}
