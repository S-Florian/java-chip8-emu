package me.sflorian.chip8;

public interface Display {
    void loeschen();

    void pixelSetzen(int x, int y, boolean weiss);

    boolean pixelGeben(int x, int y);
}
