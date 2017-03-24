package me.sflorian.chip8;

import me.sflorian.chip8.befehle.Befehl;

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

    public ProgrammBuilder laden(InputStream stream) throws IOException {
        try (DataInputStream dis = new DataInputStream(stream)) {
            if (dis.available() % 2 != 0) {
                // Ein Befehl ist 2 bytes groß. D.h. wenn die Datei nicht in byte-Paare aufteilbar ist,
                // dann ist sie ungültig.
                return this;
            }

            while (dis.available() > 0) {
                short op = dis.readShort();
                Befehl befehl = Befehl.dekodieren(op);

                if (befehl != null) {
                    befehle.add(befehl);
                    befehlAnzahl++;
                }
            }
        }

        return this;
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
