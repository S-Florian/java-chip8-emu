package me.sflorian.chip8;

import me.sflorian.chip8.benutzer.EmulatorDisplay;
import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;
import java.io.FileInputStream;

public class Chip8 {
    public static void main(String[] args) {
        EmulatorFenster fenster = new EmulatorFenster();
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);

        EmulatorDisplay display = fenster.displayGeben();
        Prozessor p = new Prozessor(new Arbeitsspeicher(), display);

        byte[] programm;

        try (FileInputStream fis = new FileInputStream("test/sprite_test.ch8")) {
            programm = new ProgrammBuilder().laden(fis).erstellen();
        } catch (Exception e) {
            programm = new byte[] { 0x00, 0x00 };
            e.printStackTrace();
        }

        p.programmLaden(programm);
        p.programmAusfuehren();
    }
}
