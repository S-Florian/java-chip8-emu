package me.sflorian.chip8.befehle.display;

import me.sflorian.chip8.Arbeitsspeicher;
import me.sflorian.chip8.Display;
import me.sflorian.chip8.DisplayKonstanten;
import me.sflorian.chip8.Prozessor;
import me.sflorian.chip8.befehle.Befehl;
import me.sflorian.chip8.befehle.helfer.EnkodierungsHelfer;

import static me.sflorian.chip8.DisplayKonstanten.*;

public class Zeichnen extends Befehl {
    private final int x, y;
    private final int hoehe;

    public Zeichnen(int x, int y, int hoehe) {
        this.x = x;
        this.y = y;
        this.hoehe = hoehe;
    }

    @Override
    public void ausfuehren(Prozessor p) {
        Display d = p.displayGeben();

        if (d == null)
            return;

        Arbeitsspeicher mem = p.arbeitsspeicherGeben();

        int hoehe = this.hoehe & 0x000F;

        for (int y = 0; y < hoehe; ++y) {
            int reihe = mem.peek(p.regIGeben() + y);

            for (int x = 0; x < 8; ++x) {
                if ((reihe & 0x80) > 0) {
                    int ix = (this.x + x) % DISPLAY_BREITE;
                    int iy = (this.y + y) % DISPLAY_HOEHE;

                    boolean pixel = d.pixelGeben(ix, iy);
                    boolean neuerPixel = !pixel;
                    d.pixelSetzen(ix, iy, neuerPixel);

                    p.regVSetzen(0xF, (byte)1);
                }

                reihe <<= 1;
            }
        }
    }

    @Override
    public short enkodieren() {
        return EnkodierungsHelfer._XY_(0xD, (byte)x, (byte)y, hoehe);
    }

    @Override
    public String alsAssembly() {
        return String.format("DRW V%1X, V%1X, %d", x, y, hoehe);
    }
}
