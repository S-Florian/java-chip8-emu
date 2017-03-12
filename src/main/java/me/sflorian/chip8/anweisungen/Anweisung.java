package me.sflorian.chip8.anweisungen;

import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.ablauf.*;
import me.sflorian.chip8.anweisungen.helfer.Bedingung;
import me.sflorian.chip8.anweisungen.helfer.IOOperation;
import me.sflorian.chip8.anweisungen.helfer.Operator;
import me.sflorian.chip8.anweisungen.io.BildschirmLoeschen;
import me.sflorian.chip8.anweisungen.io.IO;
import me.sflorian.chip8.anweisungen.io.Zufallszahl;
import me.sflorian.chip8.anweisungen.register.AddresseSetzen;
import me.sflorian.chip8.anweisungen.register.RegisterArithmetik;
import me.sflorian.chip8.anweisungen.register.RegisterHinzufuegen;
import me.sflorian.chip8.anweisungen.register.RegisterSetzen;

import static me.sflorian.chip8.anweisungen.helfer.DekodierungsHelfer.*;

/**
 * Repräsentiert eine CPU-Anweisung.
 */
public abstract class Anweisung {
    public static Anweisung dekodieren(short opcode) {
        switch (opcode) {
            case 0x0000: return new Stopp();
            case 0x00E0: return new BildschirmLoeschen();
            case 0x00EE: return new Rueckkehren();
        }

        switch (n1(opcode)) {
            case 0x1: return new Sprung(nb2(opcode), false);
            case 0x2: return new Aufrufen(nb2(opcode));
            case 0x3: return new VerzweigungKonstante(n2(opcode), b2(opcode), Bedingung.GLEICH);
            case 0x4: return new VerzweigungKonstante(n2(opcode), b2(opcode), Bedingung.UNGLEICH);
            case 0x5: return new VerzweigungRegister(n2(opcode), n3(opcode), Bedingung.GLEICH);
            case 0x6: return new RegisterSetzen(n2(opcode), b2(opcode));
            case 0x7: return new RegisterHinzufuegen(n2(opcode), b2(opcode));
            case 0x8: return new RegisterArithmetik(n2(opcode), n3(opcode), Operator.dekodieren(n4(opcode)));
            case 0x9: return new VerzweigungRegister(n2(opcode), n3(opcode), Bedingung.UNGLEICH);
            case 0xA: return new AddresseSetzen(nb2(opcode));
            case 0xB: return new Sprung(nb2(opcode), true);
            case 0xC: return new Zufallszahl(n2(opcode), b2(opcode));
            case 0xF: return new IO(n2(opcode), IOOperation.dekodieren(b2(opcode)));
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
        return String.format("%s => %s",
                getClass().getSimpleName(),
                "0x" + Integer.toHexString(enkodieren()));
    }

}
