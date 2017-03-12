package me.sflorian.chip8.befehle.helfer;

public class EnkodierungsHelfer {
    public static short _XNN(int i, byte x, byte n) {
        return (short) ((((i & 0xF) << 12) | (x << 8) | (n & 0x00FF)) & 0xFFFF);
    }

    public static short _NNN(int i, short n) {
        return (short) ((((i & 0xF) << 12) | (n & 0x0FFF)) & 0xFFFF);
    }

    public static short _XY_(int i, byte x, byte y, int j) {
        return (short) ((((i & 0xF) << 12) | ((x & 0xF) << 8) | ((y & 0xF) << 4) | (j & 0xF)) & 0xFFFF);
    }

    public static short _X__(int i, byte x, int j) {
        return (short) ((((i & 0xF) << 12) | ((x & 0xF) << 8) | (j & 0xFF)) & 0xFFFF);
    }
}
