package me.sflorian.chip8.befehle.io;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.Display;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

import static me.sflorian.chip8.DisplayKonstanten.*;

public class Zeichnen extends Befehl {
    private final int registerX, registerY;
    private final int hoehe;

    public Zeichnen(int registerX, int registerY, int hoehe) {
        this.registerX = registerX;
        this.registerY = registerY;
        this.hoehe = hoehe;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        Display d = p.displayGeben();

        if (d == null)
            return;

        Arbeitsspeicher mem = p.arbeitsspeicherGeben();

        int px = (int) p.regVGeben(registerX) & 0xFF;
        int py = (int) p.regVGeben(registerY) & 0xFF;

        int hoehe = this.hoehe & 0x000F;

        p.regVSetzen(0xF, (byte)0);

        for (int y = 0; y < hoehe; ++y) {
            int reihe = mem.peek(p.regIGeben() + y);

            for (int x = 0; x < 8; ++x) {
                if ((reihe & (0x80 >> x)) != 0) {
                    int ix = (px + x) % DISPLAY_BREITE;
                    int iy = (py + y) % DISPLAY_HOEHE;

                    boolean pixel = d.pixelGeben(ix, iy);

                    if (pixel)
                        p.regVSetzen(0xF, (byte)1);

                    d.pixelSetzen(ix, iy, !pixel);
                }
            }
        }

        d.anzeigen();
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XY_(0xD, (byte)registerX, (byte)registerY, hoehe);
    }

    @Override
    public String alsAssembly() {
        return String.format("DRW V%1X, V%1X, %d", registerX, registerY, hoehe);
    }
}
