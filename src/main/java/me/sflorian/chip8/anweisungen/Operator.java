package me.sflorian.chip8.anweisungen;

public enum Operator {
    ZUWEISEN(0x0),
    ODER(0x1),
    UND(0x2),
    ENTWEDER_ODER(0x3),
    ADDIEREN(0x4),
    SUBTRAHIEREN(0x5),
    SHIFTR_1(0x6),
    SUBTRAHIEREN_UMGEKEHRT(0x7),
    SHIFTL_1(0xE);

    private byte nummer;

    Operator(int nummer) {
        this.nummer = (byte)nummer;
    }

    public byte nummerGeben() {
        return nummer;
    }

    public static Operator dekodieren(byte b) {
        for (Operator op : Operator.values()) {
            if (b == op.nummer) return op;
        }

        return null;
    }
}