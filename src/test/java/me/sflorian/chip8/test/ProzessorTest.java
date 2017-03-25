package me.sflorian.chip8.test;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.Prozessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProzessorTest {
    @Test
    public void regVTest() {
        Prozessor p = new Prozessor(new Arbeitsspeicher());

        for (int i = -2; i < Prozessor.CHIP8_ANZAHL_V_REGISTER + 2; ++i) {
            p.regVSetzen(i, (byte)i);
        }

        assertEquals(0, p.regVGeben(-2));
        assertEquals(0, p.regVGeben(-1));

        for (int i = 0; i < Prozessor.CHIP8_ANZAHL_V_REGISTER; ++i) {
            assertEquals(i, p.regVGeben(i));
        }

        assertEquals(0, p.regVGeben(Prozessor.CHIP8_ANZAHL_V_REGISTER + 1));
    }
}
