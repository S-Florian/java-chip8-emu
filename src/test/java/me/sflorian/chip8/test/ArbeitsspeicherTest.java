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
    public void programmLadenTest() {
        Arbeitsspeicher mem = new Arbeitsspeicher();
        mem.programmLaden(new byte[] { 1, 2, 3, 4, 5 });

        // TODO: Implementieren
    }
}
