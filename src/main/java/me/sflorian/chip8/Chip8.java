package me.sflorian.chip8;

import me.sflorian.chip8.benutzer.EmulatorDisplay;
import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

public class Chip8 {
    private static void emulatorStarten(File datei) {
        EmulatorFenster fenster = new EmulatorFenster();
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);

        fenster.setTitle(String.format(EmulatorFenster.TITEL_NACHRICHT, "Läuft"));

        EmulatorDisplay display = fenster.displayGeben();
        Prozessor p = new Prozessor(new Arbeitsspeicher(), display);

        byte[] programm;

        try (FileInputStream fis = new FileInputStream(datei)) {
            programm = new ProgrammBuilder().laden(fis).erstellen();
        } catch (Exception e) {
            programm = new byte[] { 0x00, 0x00 };
            e.printStackTrace();
        }

        p.programmLaden(programm);
        p.programmAusfuehren();

        fenster.setTitle(String.format(EmulatorFenster.TITEL_NACHRICHT, "Ende"));
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Es wurde keine *.ch8 Datei angegeben!");
            System.exit(1);
            return;
        }

        String dateiname = args[0];
        File datei = new File(dateiname);

        if (!datei.exists()) {
            System.err.println(String.format("Die Datei \"%s\" gibt es nicht!", dateiname));
            System.exit(1);
            return;
        }

        emulatorStarten(datei);
    }
}
