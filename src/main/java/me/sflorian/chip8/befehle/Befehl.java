package me.sflorian.chip8.befehle;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.ablauf.*;
import me.sflorian.chip8.befehle.io.Zeichnen;
import me.sflorian.chip8.befehle.helfer.Bedingung;
import me.sflorian.chip8.befehle.helfer.IOOperation;
import me.sflorian.chip8.befehle.helfer.Operator;
import me.sflorian.chip8.befehle.io.DisplayLoeschen;
import me.sflorian.chip8.befehle.io.IO;
import me.sflorian.chip8.befehle.io.Zufallszahl;
import me.sflorian.chip8.befehle.register.AddresseSetzen;
import me.sflorian.chip8.befehle.register.RegisterArithmetik;
import me.sflorian.chip8.befehle.register.RegisterHinzufuegen;
import me.sflorian.chip8.befehle.register.RegisterSetzen;

import static me.sflorian.chip8.befehle.helfer.DekodierungsHelfer.*;

/**
 * Repräsentiert einen CPU-Befehl.
 */
public abstract class Befehl {
    public static Befehl dekodieren(short op) {
        switch (op) {
            case 0x0000: return new Stopp();
            case 0x00E0: return new DisplayLoeschen();
            case 0x00EE: return new Rueckkehren();
        }

        switch (n1(op)) {
            case 0x1: return new Sprung(nb2(op), false);
            case 0x2: return new Aufrufen(nb2(op));
            case 0x3: return new VerzweigungKonstante(n2(op), b2(op), Bedingung.GLEICH);
            case 0x4: return new VerzweigungKonstante(n2(op), b2(op), Bedingung.UNGLEICH);
            case 0x5: return new VerzweigungRegister(n2(op), n3(op), Bedingung.GLEICH);
            case 0x6: return new RegisterSetzen(n2(op), b2(op));
            case 0x7: return new RegisterHinzufuegen(n2(op), b2(op));
            case 0x8: return new RegisterArithmetik(n2(op), n3(op), Operator.dekodieren(n4(op)));
            case 0x9: return new VerzweigungRegister(n2(op), n3(op), Bedingung.UNGLEICH);
            case 0xA: return new AddresseSetzen(nb2(op));
            case 0xB: return new Sprung(nb2(op), true);
            case 0xC: return new Zufallszahl(n2(op), b2(op));
            case 0xD: return new Zeichnen(n2(op), n3(op), n4(op));
            case 0xF: return new IO(n2(op), IOOperation.dekodieren(b2(op)));
        }

        return null;
    }

    public abstract void ausfuehren(Prozessor prozessor);

    public abstract short enkodieren();

    public abstract String alsAssembly();

    @Override
    public int hashCode() {
        return enkodieren();
    }

    @Override
    public String toString() {
        return String.format("%s (0x%04X)", alsAssembly(), enkodieren());
    }

}
