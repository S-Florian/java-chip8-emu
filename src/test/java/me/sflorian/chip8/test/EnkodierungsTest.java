package me.sflorian.chip8.test;

import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnkodierungsTest {
    @Test
    public void _XNNTest() {
        short e = EnkodierungsHelfer._XNN(6, (byte)0xA, (byte)0xEF);
        assertEquals((short)0x6AEF, e);
    }

    @Test
    public void _NNNTest() {
        short e = EnkodierungsHelfer._NNN(1, (short)0xABA);
        assertEquals((short)0x1ABA, e);
    }

    @Test
    public void _XY_Test() {
        short e = EnkodierungsHelfer._XY_(8, (byte)0x3, (byte)0x7, 2);
        assertEquals((short)0x8372, e);
    }

    @Test
    public void _X__Test() {
        short e = EnkodierungsHelfer._X__(0xF, (byte)0x2, 0x34);
        assertEquals((short)0xF234, e);
    }
}
