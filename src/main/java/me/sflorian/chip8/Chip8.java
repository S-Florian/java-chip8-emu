package me.sflorian.chip8;

import me.sflorian.chip8.befehle.display.Zeichnen;
import me.sflorian.chip8.befehle.helfer.IOOperation;
import me.sflorian.chip8.befehle.io.IO;
import me.sflorian.chip8.befehle.register.AddresseSetzen;
import me.sflorian.chip8.befehle.register.RegisterSetzen;
import me.sflorian.chip8.benutzer.EmulatorDisplay;
import me.sflorian.chip8.benutzer.EmulatorFenster;

import javax.swing.*;

public class Chip8 {
    public static void main(String[] args) {
        EmulatorFenster fenster = new EmulatorFenster();
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        fenster.setVisible(true);

        EmulatorDisplay display = fenster.displayGeben();
        Prozessor p = new Prozessor(new Arbeitsspeicher(), display);

        byte[] programm = new ProgrammBuilder()
            .mit(new RegisterSetzen(0x0, (byte)0b00011000))
            .mit(new RegisterSetzen(0x1, (byte)0b00111101))
            .mit(new RegisterSetzen(0x2, (byte)0b01111110))
            .mit(new AddresseSetzen((short)0x0))
            .mit(new IO(0x2, IOOperation.AS_SPEICHERN))
            .mit(new Zeichnen(4, 4, 3))
            .erstellen();

        p.programmLaden(programm);
        p.programmAusfuehren();
    }
}
