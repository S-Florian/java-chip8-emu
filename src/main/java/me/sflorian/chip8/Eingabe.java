package me.sflorian.chip8;

public interface Eingabe {
    boolean wirdGedrueckt(int taste);

    void eingabeListenerHinzufuegen(EingabeListener listener);

    void eingabeListenerEntfernen(EingabeListener listener);
}
