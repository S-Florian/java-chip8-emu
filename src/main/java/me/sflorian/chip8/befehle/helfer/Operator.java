package me.sflorian.chip8.befehle.helfer;

public enum Operator {
    ZUWEISEN(0x0, "LD"),
    ODER(0x1, "OR"),
    UND(0x2, "AND"),
    ENTWEDER_ODER(0x3, "XOR"),
    ADDIEREN(0x4, "ADD"),
    SUBTRAHIEREN(0x5, "SUB"),
    SHIFTR_1(0x6, "SHR"),
    SUBTRAHIEREN_UMGEKEHRT(0x7, "SUBN"),
    SHIFTL_1(0xE, "SHL");

    private final byte nummer;
    private final String name;

    Operator(int nummer, String name) {
        this.nummer = (byte)nummer;
        this.name = name;
    }

    public byte nummerGeben() {
        return nummer;
    }

    public String nameGeben() {
        return name;
    }

    public static Operator dekodieren(byte b) {
        for (Operator op : Operator.values())
            if (b == op.nummer) return op;

        return null;
    }
}