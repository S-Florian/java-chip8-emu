package me.sflorian.chip8.befehle.register;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;
import me.sflorian.chip8.befehle.helfer.Operator;

public class RegisterArithmetik extends Befehl {
    private final int registerA, registerB;
    private final Operator operator;

    public RegisterArithmetik(int registerA, int registerB, Operator operator) {
        this.registerA = registerA;
        this.registerB = registerB;
        this.operator = operator;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        if (operator == null) return;

        switch (operator) {
            case ZUWEISEN:
                p.regVSetzen(registerA, p.regVGeben(registerB));
                break;

            case ODER:
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) | p.regVGeben(registerB)));
                break;

            case UND:
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) & p.regVGeben(registerB)));
                break;

            case ENTWEDER_ODER:
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) ^ p.regVGeben(registerB)));
                break;

            case ADDIEREN:
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) + p.regVGeben(registerB)));
                p.regVSetzen(0xF, (byte) (p.regVGeben(registerA) > 255 ? 1 : 0));
                break;

            case SUBTRAHIEREN:
                p.regVSetzen(0xF, (byte) (p.regVGeben(registerA) > p.regVGeben(registerB) ? 1 : 0));
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) - p.regVGeben(registerB)));
                break;

            case SUBTRAHIEREN_UMGEKEHRT:
                p.regVSetzen(0xF, (byte) (p.regVGeben(registerB) > p.regVGeben(registerA) ? 1 : 0));
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerB) - p.regVGeben(registerA)));
                break;

            case SHIFTR_1:
                p.regVSetzen(0xF, (byte) (p.regVGeben(registerA) & 0x1));
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) >> 1));
                break;

            case SHIFTL_1:
                p.regVSetzen(0xF, (byte) (p.regVGeben(registerA) & 0x80));
                p.regVSetzen(registerA, (byte)(p.regVGeben(registerA) << 1));
                break;
        }
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XY_(0x8, (byte)registerA, (byte)registerB, operator.nummerGeben());
    }

    @Override
    public String alsAssembly() {
        switch (operator) {
            case SHIFTL_1: return String.format("SHL V%1X", registerA);
            case SHIFTR_1: return String.format("SHR V%1X", registerB);
            default:
                return String.format("%s V%1X, V%1X", operator.nameGeben(), registerA, registerB);
        }

    }
}
