package me.sflorian.chip8;

import me.sflorian.chip8.benutzer.EmulatorDisplay;
import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

import static javax.swing.JOptionPane.*;

public class Chip8 {
    private static boolean emulatorStarten(File datei) {
        byte[] programm;

        try (FileInputStream fis = new FileInputStream(datei)) {
            programm = ProgrammBuilder.laden(fis);
        } catch (Exception e) {
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
        Prozessor p = new Prozessor(new Arbeitsspeicher(), display);

        p.programmLaden(programm);
        p.programmAusfuehren();

        fenster.setTitle(String.format(EmulatorFenster.TITEL_NACHRICHT, "Ende"));
        return true;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Es wurde keine *.ch8 Datei angegeben!");
            System.exit(1);
            return;
        }

        String dateiname = args[0];
        File datei = new File(dateiname);

        if (!emulatorStarten(datei))
            System.exit(1);
    }
}
