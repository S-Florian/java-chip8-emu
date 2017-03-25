package me.sflorian.chip8.benutzer;

import me.sflorian.chip8.Chip8;
import me.sflorian.chip8.Display;
import me.sflorian.chip8.ProgrammBuilder;
import me.sflorian.chip8.Prozessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class EmulatorFenster extends JFrame implements ActionListener {
    private EmulatorDisplay display;

    public static final String TITEL = "CHIP-8 Emulator";
    public static final String TITEL_NACHRICHT = "CHIP-8 Emulator [%s]";

    private static final int[] CPU_GESCHWINDIGKEITEN = new int[] {
        60, 120, 240, 480, 960
    };

    private static final int STANDARD_GESCHWINDIGKEIT = 240;

    private Prozessor prozessor;

    public EmulatorFenster() {
        super(TITEL);
        setLayout(new BorderLayout());
        setResizable(false);
        add(display = new EmulatorDisplay(), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        {
            JMenu datei = new JMenu("Datei");
            {
                JMenuItem beenden = new JMenuItem("Beenden");
                beenden.setActionCommand("beenden");
                beenden.addActionListener(this);
                datei.add(beenden);
            }
            menuBar.add(datei);

            JMenu einstellungen = new JMenu("Einstellungen");
            {
                JMenu geschwindigkeitMenue = new JMenu("CPU-Geschwindigkeit");
                {
                    ButtonGroup group = new ButtonGroup();

                    for (final int f : CPU_GESCHWINDIGKEITEN) {
                        JRadioButtonMenuItem g = new JRadioButtonMenuItem(f + " Hz");
                        group.add(g);

                        if (f == STANDARD_GESCHWINDIGKEIT)
                            g.setSelected(true);

                        g.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (prozessor != null) {
                                    prozessor.msProZyklusSetzen(1000 / f);
                                }
                            }
                        });
                        geschwindigkeitMenue.add(g);
                    }
                }
                einstellungen.add(geschwindigkeitMenue);
            }
            menuBar.add(einstellungen);
        }
        add(menuBar, BorderLayout.NORTH);

        pack();
    }

    public void prozessorSetzen(Prozessor p) {
        prozessor = p;
    }

    public EmulatorDisplay displayGeben() {
        return display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "beenden":
                dispatchEvent(new WindowEvent(EmulatorFenster.this, WindowEvent.WINDOW_CLOSING));
                break;
        }
    }
}
