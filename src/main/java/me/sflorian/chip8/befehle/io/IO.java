package me.sflorian.chip8.befehle.io;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;
import me.sflorian.chip8.befehle.helfer.IOOperation;
import me.sflorian.chip8.helfer.Zahlen;

public class IO extends Befehl {
    private final int register;
    private final IOOperation operation;

    public IO(int register, IOOperation operation) {
        if (!Prozessor.istGueltigerRegisterIndex(register))
            throw new IllegalArgumentException("register muss zw. 0x0 und 0xF liegen (inklusiv)!");

        this.register = register;
        this.operation = operation;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        if (operation == null) return;

        switch (operation) {
            case DT_ABFRAGEN:
                p.regVSetzen(register, p.regDTGeben());
                break;

            case TASTE_ABFRAGEN:
                p.aufTasteWarten(register);
                break;

            case DT_SETZEN:
                p.regDTSetzen(p.regVGeben(register));
                break;

            case ST_SETZEN:
                p.regSTSetzen(p.regVGeben(register));
                break;

            case ZU_I_HINZUFUEGEN:
                p.regISetzen((short) (p.regIGeben() + p.regVGeben(register)));
                break;

            case ZAHL_IN_I: {
                int x = (int) p.regVGeben(register) & 0xFF;
                p.regISetzen((short) (x * Zahlen.ZAHL_HOEHE));
                break;
            }

            case BCD_IN_I: {
                int x = (int) p.regVGeben(register) & 0xFF;

                byte[] bcd = new byte[] {
                    (byte) (x / 100),
                    (byte) ((x % 100) / 10),
                    (byte) (x % 10)
                };

                Arbeitsspeicher mem = p.arbeitsspeicherGeben();
                mem.multiPoke(p.regIGeben(), bcd);
                break;
            }

            case AS_SPEICHERN:
                p.registerSpeichern(0, register, p.regIGeben());
                break;

            case AS_LADEN:
                p.registerLaden(0, register, p.regIGeben());
                break;
        }
    }

    @Override
    public short enkodieren() {
        if (operation == null)
            return 0x0000;

        return EnkodierungsHelfer._X__(0xF, (byte)register, operation.nummerGeben());
    }

    @Override
    public String alsAssembly() {
        String asmFormat = operation != null ? operation.asmFormatGeben() : "IO V%01X";
        return String.format(asmFormat, register);
    }
}
