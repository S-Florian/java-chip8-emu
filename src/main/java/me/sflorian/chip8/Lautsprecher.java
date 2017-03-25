package me.sflorian.chip8;

public interface Lautsprecher {
    void tonAbspielen();

    void tonStoppen();

    boolean kannTonAbspielen();

    boolean wirdAbgespielt();
}
