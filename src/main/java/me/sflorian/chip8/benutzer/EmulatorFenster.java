package me.sflorian.chip8.benutzer;

import javax.swing.*;
import java.awt.*;

public class EmulatorFenster extends JFrame {
    private EmulatorDisplay display;

    public static final String TITEL = "CHIP-8 Emulator";
    public static final String TITEL_NACHRICHT = "CHIP-8 Emulator [%s]";

    public EmulatorFenster() {
        super(TITEL);
        setLayout(new BorderLayout());
        setResizable(false);
        add(display = new EmulatorDisplay(), BorderLayout.CENTER);
        pack();
    }

    public EmulatorDisplay displayGeben() {
        return display;
    }
}
