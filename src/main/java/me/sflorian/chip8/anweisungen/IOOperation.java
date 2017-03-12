package me.sflorian.chip8.anweisungen;

public enum IOOperation {
    DT_ABFRAGEN(0x07, "LD V%1X, DT"),
    TASTE_ABFRAGEN(0x0A, "LD V%1X, K"),
    DT_SETZEN(0x15, "LD DT, V%1X"),
    ST_SETZEN(0x18, "LD ST, V%1X"),
    ZU_I_HINZUFUEGEN(0x1E, "ADD I, V%1X"),
    BUCHSTABE_IN_I(0x29, "LD F, V%1X"),
    BCD_IN_I(0x33, "LD B, V%1X"),
    AS_SPEICHERN(0x55, "LD [I], V%1X"),
    AS_LADEN(0x65, "LD V%1X, [I]");

    private final int nummer;
    private final String asmFormat;

    public int nummerGeben() {
        return nummer;
    }

    public String asmFormatGeben() {
        return asmFormat;
    }

    IOOperation(int nummer, String asmFormat) {
        this.nummer = nummer;
        this.asmFormat = asmFormat;
    }

    public static IOOperation dekodieren(byte b) {
        for (IOOperation op : IOOperation.values())
            if (b == op.nummer) return op;

        return null;
    }
}
