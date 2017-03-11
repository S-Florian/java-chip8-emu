package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.ProgrammBuilder;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.anweisungen.RegisterSetzen;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProgrammTest {
    @Test
    public void registerSetzenTest() {
        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)42))
            .mit(new RegisterSetzen(0x1, (byte)66))
            .mit(new RegisterSetzen(0xF, (byte)88))
            .erstellen();

        Prozessor p = new Prozessor(new Arbeitsspeicher());
        p.programmLaden(programm, true);
        p.programmAusfuehren();

        assertEquals((byte)42, p.regVGeben(0x0));
        assertEquals((byte)66, p.regVGeben(0x1));
        assertEquals((byte)88, p.regVGeben(0xF));
    }
}
