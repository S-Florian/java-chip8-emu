package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.Disassembly;
import me.sflorian.chip8.ProgrammBuilder;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.*;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ProgrammTest {
    private static Prozessor ausfuehren(byte[] programm) {
        for (byte b : programm) {
            System.out.print(Integer.toHexString((int)b & 0xFF) + "; ");
        }

        System.out.println();
        System.out.println(Disassembly.generieren(programm));

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm);
        p.programmAusfuehren();

        return p;
    }

    @Test
    public void registerManipulationTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)42))
            .mit(new RegisterSetzen(0x1, (byte)66))
            .mit(new RegisterSetzen(0xF, (byte)88))
            .mit(new RegisterHinzufuegen(0xF, (byte)12))
            .mit(new AddresseSetzen((short) 0x200))
            .erstellen();

        Prozessor p = ausfuehren(programm);

        assertEquals((byte)42, p.regVGeben(0x0));
        assertEquals((byte)66, p.regVGeben(0x1));
        assertEquals((byte)100, p.regVGeben(0xF));
        assertEquals((short)0x200, p.regIGeben());
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

        Prozessor p = ausfuehren(programm);

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

        Prozessor p = ausfuehren(programm);

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

        Prozessor p = ausfuehren(programm);

        assertEquals(70, (int) p.regVGeben(0x0) & 0xFF);
        assertEquals(0, (int) p.regVGeben(0x1) & 0xFF);
        assertEquals(0, (int) p.regVGeben(0x2) & 0xFF);
        assertEquals(73, (int) p.regVGeben(0x3) & 0xFF);
    }

    @Test
    public void sprungVerschobenTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)20))
            .mit(new Sprung((short) 30, true))
            .erstellen();

        Prozessor p = ausfuehren(programm);

        assertEquals((short)52, p.regPCGeben());
    }

    @Test
    public void zufallszahlTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new Zufallszahl(0x1, (byte)0xFF))
            .erstellen();

        Prozessor p = ausfuehren(programm);

        System.out.println((int) p.regVGeben(0x1) & 0xFF);
    }

    @Test
    public void funktionsTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new Aufrufen((short) (Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION + 8)))
            .mit(new Aufrufen((short) (Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION + 8)))
            .mit(new RegisterSetzen(0x3, (byte)1))
            .mit(new Stopp())
            .mit(new RegisterHinzufuegen(0x0, (byte)2))
            .mit(new RegisterHinzufuegen(0x1, (byte)1))
            .mit(new RegisterHinzufuegen(0x2, (byte)3))
            .mit(new Rueckkehren())
            .erstellen();

        Prozessor p = ausfuehren(programm);

        assertEquals((byte)4, p.regVGeben(0x0));
        assertEquals((byte)2, p.regVGeben(0x1));
        assertEquals((byte)6, p.regVGeben(0x2));
        assertEquals((byte)1, p.regVGeben(0x3));
    }

    @Test
    public void registerSpeichernUndLadenTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x5, (byte)16))
            .mit(new IO(0x5, IOOperation.ZU_I_HINZUFUEGEN))
            .mit(new RegisterSetzen(0x0, (byte)0))
            .mit(new RegisterSetzen(0x1, (byte)1))
            .mit(new RegisterSetzen(0x2, (byte)2))
            .mit(new RegisterSetzen(0x3, (byte)3))
            .mit(new RegisterSetzen(0x4, (byte)4))
            .mit(new IO(0x4, IOOperation.AS_SPEICHERN))
            .mit(new RegisterSetzen(0x0, (byte)0))
            .mit(new RegisterSetzen(0x1, (byte)0))
            .mit(new RegisterSetzen(0x2, (byte)0))
            .mit(new RegisterSetzen(0x3, (byte)0))
            .mit(new RegisterSetzen(0x4, (byte)0))
            .mit(new IO(0x4, IOOperation.AS_LADEN))
            .erstellen();

        Prozessor p = ausfuehren(programm);
        Arbeitsspeicher mem = p.arbeitsspeicherGeben();

        assertEquals((short)16, p.regIGeben());
        assertArrayEquals(new byte[] { 0, 1, 2, 3, 4 }, mem.multiPeek((short)16, 5));
        assertEquals((byte)0, p.regVGeben(0x0));
        assertEquals((byte)1, p.regVGeben(0x1));
        assertEquals((byte)2, p.regVGeben(0x2));
        assertEquals((byte)3, p.regVGeben(0x3));
        assertEquals((byte)4, p.regVGeben(0x4));
    }
}
