package me.sflorian.chip8;

import me.sflorian.chip8.befehle.Befehl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ProgrammBuilder {
    private final List<Befehl> befehle = new LinkedList<>();
    private int befehlAnzahl = 0;

    public ProgrammBuilder mit(Befehl befehl) {
        befehle.add(befehl);
        befehlAnzahl++;
        return this;
    }

    public static byte[] laden(InputStream stream) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        int gelesen;
        byte[] puffer = new byte[512];

        while ((gelesen = stream.read(puffer, 0, puffer.length)) != -1)
            bao.write(puffer, 0, gelesen);

        bao.flush();
        return bao.toByteArray();
    }

    public byte[] erstellen() {
        byte[] programm = new byte[befehlAnzahl * 2];

        int i = 0;

        for (Befehl a : befehle) {
            short op = a.enkodieren();
            programm[i] = (byte)((op >> 8) & 0xFF);
            programm[i + 1] = (byte)(op & 0xFF);
            i += 2;
        }

        return programm;
    }
}
