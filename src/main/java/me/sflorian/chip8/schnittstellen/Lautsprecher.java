package me.sflorian.chip8.schnittstellen;

public interface Lautsprecher {
    void tonAbspielen();

    void tonStoppen();

    boolean kannTonAbspielen();

    boolean wirdAbgespielt();
}
