package me.sflorian.chip8.befehle.helfer;

import me.sflorian.chip8.Prozessor;

public class Verzweigung {
    public static void verarbeiten(Prozessor p, byte a, byte b, Bedingung bedingung) {
        if (bedingung == null)
            return;

        switch (bedingung) {
            case GLEICH:
                if (a == b) p.weiter();
                break;

            case UNGLEICH:
                if (a != b) p.weiter();
                break;

            default:
                break;
        }
    }
}
