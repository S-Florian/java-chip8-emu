package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;

/**
 * Repräsentiert eine CPU-Anweisung.
 */
public abstract class Anweisung {
    private static byte n1(short b) {
        return (byte) ((b & 0xF000) >> 12);
    }

    private static byte n2(short b) {
        return (byte) ((b & 0x0F00) >> 8);
    }

    private static byte n3(short b) {
        return (byte) ((b & 0x00F0) >> 4);
    }

    private static byte n4(short b) {
        return (byte) (b & 0x000F);
    }

    private static byte b1(short b) {
        return (byte) ((b & 0xFF00) >> 8);
    }

    private static byte b2(short b) {
        return (byte) (b & 0x00FF);
    }

    private static byte nb1(short b) {
        return (byte) (b & 0xFFF0);
    }

    private static byte nb2(short b) {
        return (byte) (b & 0x0FFF);
    }

    public static Anweisung dekodieren(short opcode) {
        switch (n1(opcode)) {
            case 0x0: {
                switch ((int)opcode) {
                    case 0x00E0: return new BildschirmLoeschen();
                    case 0x00EE: break; // TODO
                }

                break;
            }

            case 0x1: return new Sprung(nb2(opcode));
            case 0x3: return new VerzweigungKonstante(n2(opcode), b2(opcode), Bedingung.GLEICH);
            case 0x4: return new VerzweigungKonstante(n2(opcode), b2(opcode), Bedingung.UNGLEICH);
            case 0x5: return new VerzweigungRegister(n2(opcode), n3(opcode), Bedingung.GLEICH);
            case 0x6: return new RegisterSetzen(n2(opcode), b2(opcode));
            case 0x7: return new RegisterHinzufuegen(n2(opcode), b2(opcode));
            case 0x8: return new RegisterArithmetik(n2(opcode), n3(opcode), Operator.dekodieren(n4(opcode)));
            case 0x9: return new VerzweigungRegister(n2(opcode), n3(opcode), Bedingung.UNGLEICH);
        }

        return null;
    }

    public abstract void ausfuehren(Prozessor prozessor);

    public abstract short enkodieren();

    @Override
    public int hashCode() {
        return enkodieren();
    }

    @Override
    public String toString() {
        return String.format("%s => %s",
                getClass().getSimpleName(),
                "0x" + Integer.toHexString(enkodieren()));
    }
}
