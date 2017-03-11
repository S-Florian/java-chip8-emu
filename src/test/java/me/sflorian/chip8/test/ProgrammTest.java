package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.ProgrammBuilder;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.*;

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

    @Test
    public void bedingungsTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)70))
            .mit(new RegisterSetzen(0x1, (byte)30))
            .mit(new RegisterArithmetik(0x0, 0x1, Operator.SUBTRAHIEREN))
            .mit(new VerzweigungKonstante(0xF, (byte)1, Bedingung.UNGLEICH))
            .mit(new RegisterSetzen(0xE, (byte)0x85))
            .erstellen();

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm);
        p.programmAusfuehren();

        assertEquals(0x85, (int) p.regVGeben(0xE) & 0xFF);
    }

    public void sprungTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)70))
            .mit(new Sprung((short) (Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION + 8)))
            .mit(new RegisterSetzen(0x1, (byte)71))
            .mit(new RegisterSetzen(0x2, (byte)72))
            .mit(new RegisterSetzen(0x3, (byte)73))
            .erstellen();

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm);
        p.programmAusfuehren();

        assertEquals(70, (int) p.regVGeben(0x0) & 0xFF);
        assertEquals(0, (int) p.regVGeben(0x1) & 0xFF);
        assertEquals(0, (int) p.regVGeben(0x2) & 0xFF);
        assertEquals(73, (int) p.regVGeben(0x3) & 0xFF);
    }
}
