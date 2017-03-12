package me.sflorian.chip8.anweisungen.helfer;

public class DekodierungsHelfer {
    public static byte n1(short b) {
        return (byte) ((b & 0xF000) >> 12);
    }

    public static byte n2(short b) {
        return (byte) ((b & 0x0F00) >> 8);
    }

    public static byte n3(short b) {
        return (byte) ((b & 0x00F0) >> 4);
    }

    public static byte n4(short b) {
        return (byte) (b & 0x000F);
    }

    public static byte b1(short b) {
        return (byte) ((b & 0xFF00) >> 8);
    }

    public static byte b2(short b) {
        return (byte) (b & 0x00FF);
    }

    public static short nb1(short b) {
        return (short) ((b & 0xFFF0) >> 4);
    }

    public static short nb2(short b) {
        return (short) (b & 0x0FFF);
    }

}
