package me.sflorian.chip8.benutzer;

import me.sflorian.chip8.Display;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EmulatorDisplay extends JComponent implements Display {
    private static final int PIXEL_GROESSE = 16;

    private static final int DISPLAY_BREITE = 64;
    private static final int DISPLAY_HOEHE = 32;

    // In der Matrix bedeutet true weiß und false schwarz.
    private boolean[][] pixel = new boolean[DISPLAY_HOEHE][DISPLAY_BREITE];

    public EmulatorDisplay() {
        Dimension d = new Dimension(DISPLAY_BREITE * PIXEL_GROESSE, DISPLAY_HOEHE * PIXEL_GROESSE);
        setSize(d);
        setPreferredSize(d);

        for (int y = 0; y < pixel.length; ++y) {
            for (int x = 0; x < pixel[y].length; ++x) {
                pixel[y][x] = new Random().nextBoolean();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);

        for (int y = 0; y < pixel.length; ++y) {
            for (int x = 0; x < pixel[y].length; ++x) {
                g.setColor(pixel[y][x] ? Color.WHITE : Color.BLACK);
                g.fillRect(x * PIXEL_GROESSE, y * PIXEL_GROESSE, PIXEL_GROESSE, PIXEL_GROESSE);
            }
        }
    }

    @Override
    public void loeschen() {
        for (int y = 0; y < pixel.length; ++y) {
            for (int x = 0; x < pixel[y].length; ++x) {
                pixel[y][x] = false;
            }
        }
    }

    @Override
    public void pixelSetzen(int x, int y, boolean weiss) {
        pixel[y % DISPLAY_HOEHE][x % DISPLAY_BREITE] = weiss;
    }
}
