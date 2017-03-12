package me.sflorian.chip8;

import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;

public class Chip8 {
    public static void main(String[] args) {
        EmulatorFenster fenster = new EmulatorFenster();
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);
    }
}
