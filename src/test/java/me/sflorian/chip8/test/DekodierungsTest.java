package me.sflorian.chip8.test;

import me.sflorian.chip8.anweisungen.DekodierungsHelfer;
import org.junit.Test;
import static org.junit.Assert.*;

public class DekodierungsTest {
    @Test
    public void n1Test() {
        assertEquals((byte)0xA, DekodierungsHelfer.n1((short) 0xABCD));
    }

    @Test
    public void n2Test() {
        assertEquals((byte)0xB, DekodierungsHelfer.n2((short) 0xABCD));
    }

    @Test
    public void n3Test() {
        assertEquals((byte)0xC, DekodierungsHelfer.n3((short) 0xABCD));
    }

    @Test
    public void n4Test() {
        assertEquals((byte)0xD, DekodierungsHelfer.n4((short) 0xABCD));
    }

    @Test
    public void b1Test() {
        assertEquals((byte)0xAB, DekodierungsHelfer.b1((short) 0xABCD));
    }

    @Test
    public void b2Test() {
        assertEquals((byte)0xCD, DekodierungsHelfer.b2((short) 0xABCD));
    }

    @Test
    public void nb1Test() {
        assertEquals((short)0xABC, DekodierungsHelfer.nb1((short) 0xABCD));
    }

    @Test
    public void nb2Test() {
        assertEquals((short)0xBCD, DekodierungsHelfer.nb2((short) 0xABCD));
    }
}
