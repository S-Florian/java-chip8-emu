package me.sflorian.chip8.benutzer;

import javax.swing.*;
import java.awt.*;

public class EmulatorFenster extends JFrame {
    private EmulatorDisplay display;

    public EmulatorFenster() {
        super("CHIP-8 Emulator");
        setLayout(new BorderLayout());
        setResizable(false);
        add(display = new EmulatorDisplay(), BorderLayout.CENTER);
        pack();
    }

    public EmulatorDisplay displayGeben() {
        return display;
    }
}
