package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.ProgrammBuilder;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.Operator;
import me.sflorian.chip8.anweisungen.RegisterArithmetik;
import me.sflorian.chip8.anweisungen.RegisterHinzufuegen;
import me.sflorian.chip8.anweisungen.RegisterSetzen;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProgrammTest {
    @Test
    public void registerManipulationTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)42))
            .mit(new RegisterSetzen(0x1, (byte)66))
            .mit(new RegisterSetzen(0xF, (byte)88))
            .mit(new RegisterHinzufuegen(0xF, (byte)12))
            .erstellen();

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm);
        p.programmAusfuehren();

        assertEquals((byte)42, p.regVGeben(0x0));
        assertEquals((byte)66, p.regVGeben(0x1));
        assertEquals((byte)100, p.regVGeben(0xF));
    }

    @Test
    public void registerArithmetikTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)80))
            .mit(new RegisterSetzen(0x1, (byte)20))
            .mit(new RegisterArithmetik(0x0, 0x1, Operator.ADDIEREN))
            .mit(new RegisterSetzen(0x1, (byte)50))
            .mit(new RegisterArithmetik(0x0, 0x1, Operator.SUBTRAHIEREN))
            .erstellen();

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm);
        p.programmAusfuehren();

        assertEquals(50, p.regVGeben(0x0));
    }
}
