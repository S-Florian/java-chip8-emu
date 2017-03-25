package me.sflorian.chip8.benutzer;

import me.sflorian.chip8.schnittstellen.Display;
import me.sflorian.chip8.schnittstellen.Eingabe;
import me.sflorian.chip8.schnittstellen.EingabeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.awt.event.KeyEvent.*;
import static me.sflorian.chip8.helfer.DisplayKonstanten.DISPLAY_BREITE;
import static me.sflorian.chip8.helfer.DisplayKonstanten.DISPLAY_HOEHE;

public class EmulatorDisplay extends JComponent implements Display, Eingabe, KeyListener {
    private static final Map<Integer, Integer> TASTEN_ZUORDNUNG = new HashMap<>();

    static {
        Map<Integer, Integer> t = TASTEN_ZUORDNUNG;
        t.put(VK_1, 0x1); t.put(VK_2, 0x2); t.put(VK_3, 0x3); t.put(VK_4, 0xC);
        t.put(VK_Q, 0x4); t.put(VK_W, 0x5); t.put(VK_E, 0x6); t.put(VK_R, 0xD);
        t.put(VK_A, 0x7); t.put(VK_S, 0x8); t.put(VK_D, 0x9); t.put(VK_F, 0xE);
        t.put(VK_Z, 0xA); t.put(VK_X, 0x0); t.put(VK_C, 0xB); t.put(VK_V, 0xF);
        t.put(VK_Y, 0xA);
    }

    private static final int PIXEL_GROESSE = 16;

    // In der Matrix bedeutet true weiß und false schwarz.
    private boolean[][] pixel = new boolean[DISPLAY_HOEHE][DISPLAY_BREITE];

    private final boolean[] gedrueckteTasten = new boolean[16];
    private List<EingabeListener> eingabeListener = new LinkedList<>();

    public EmulatorDisplay() {
        Dimension d = new Dimension(DISPLAY_BREITE * PIXEL_GROESSE, DISPLAY_HOEHE * PIXEL_GROESSE);
        setSize(d);
        setPreferredSize(d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

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
        pixel[y][x] = weiss;
    }

    @Override
    public boolean pixelGeben(int x, int y) {
        return pixel[y][x];
    }

    @Override
    public void anzeigen() {
        repaint();
    }

    @Override
    public boolean wirdGedrueckt(int taste) {
        // Tasten die es nicht gibt, werden nicht gedrückt.
        if (taste < 0x0 || taste > 0xF)
            return false;

        return gedrueckteTasten[taste];
    }

    @Override
    public void eingabeListenerHinzufuegen(EingabeListener listener) {
        eingabeListener.add(listener);
    }

    @Override
    public void eingabeListenerEntfernen(EingabeListener listener) {
        eingabeListener.remove(listener);
    }

    @Override
    public Eingabe eingabeGeben() {
        return this;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int taste = e.getKeyCode();

        if (TASTEN_ZUORDNUNG.containsKey(taste)) {
            int ch8Taste = TASTEN_ZUORDNUNG.get(taste);
            gedrueckteTasten[ch8Taste] = true;

            if (eingabeListener != null) {
                for (EingabeListener l : eingabeListener)
                    l.tasteGedrueckt(ch8Taste);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int taste = e.getKeyCode();

        if (TASTEN_ZUORDNUNG.containsKey(taste)) {
            int ch8Taste = TASTEN_ZUORDNUNG.get(taste);
            gedrueckteTasten[ch8Taste] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
