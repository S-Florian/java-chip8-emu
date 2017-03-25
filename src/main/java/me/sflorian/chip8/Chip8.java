package me.sflorian.chip8;

import me.sflorian.chip8.benutzer.EmulatorDisplay;
import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import static javax.swing.JOptionPane.*;

public class Chip8 {
    private static boolean emulatorStarten(File datei) {
        byte[] programm;

        try (FileInputStream fis = new FileInputStream(datei)) {
            programm = ProgrammBuilder.laden(fis);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Konnte Datei nicht laden:\n" + e.getLocalizedMessage(), "Fehler", ERROR_MESSAGE);
            return false;
        }

        if (programm == null) {
            JOptionPane.showMessageDialog(null, "Diese CHIP-8 Datei ist korrupt!", "Fehler", ERROR_MESSAGE);
            return false;
        }

        EmulatorFenster fenster = new EmulatorFenster();
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);

        fenster.setTitle(String.format(EmulatorFenster.TITEL_NACHRICHT, "Läuft"));

        EmulatorDisplay display = fenster.displayGeben();
        fenster.addKeyListener(display);

        try (Prozessor p = new Prozessor(new Arbeitsspeicher(), display)) {
            fenster.prozessorSetzen(p);
            p.programmLaden(programm);

            while (true) {
                if (p.istGestoppt()) {
                    p.programmAusfuehren();
                }
            }
        }

        //fenster.setTitle(String.format(EmulatorFenster.TITEL_NACHRICHT, "Ende"));
    }

    public static File ch8DateiOeffnen() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setFileFilter(new FileNameExtensionFilter("CHIP-8 ROMs", "ch8", "chip8"));
        chooser.setMultiSelectionEnabled(false);
        chooser.showOpenDialog(null);

        return chooser.getSelectedFile();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final File datei;

        if (args.length < 1) {
            datei = ch8DateiOeffnen();

            if (datei == null)
                return;
        } else {
            datei = new File(args[0]);
        }

        if (!emulatorStarten(datei))
            System.exit(1);
    }
}
