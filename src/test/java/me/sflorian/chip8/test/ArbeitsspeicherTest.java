package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArbeitsspeicherTest {
    @Test
    public void peekPokeTest() {
        Arbeitsspeicher mem = new Arbeitsspeicher();
        mem.poke(0x0, (byte)56);
        mem.poke(0x1, (byte)39);
        mem.poke(0x2, (byte)66);

        assertEquals((byte)56, mem.peek(0x0));
        assertEquals((byte)39, mem.peek(0x1));
        assertEquals((byte)66, mem.peek(0x2));
    }

    @Test
    public void multiPeekPokeTest() {
        Arbeitsspeicher mem = new Arbeitsspeicher();
        mem.multiPoke(0x0, new byte[] { 56, 39, 66 });
        assertArrayEquals(new byte[] { 56, 39, 66 }, mem.multiPeek(0x0, 3));
    }

    @Test
    public void programmLadenTest() {
        byte[] testprogramm = new byte[] { 1, 2, 3, 4, 5 };

        Arbeitsspeicher mem = new Arbeitsspeicher();
        mem.programmLaden(testprogramm);
        byte[] b = mem.multiPeek(Arbeitsspeicher.CHIP8_AS_PROGRAMM_POSITION, testprogramm.length);
        
        assertArrayEquals(b, testprogramm);
    }
}
