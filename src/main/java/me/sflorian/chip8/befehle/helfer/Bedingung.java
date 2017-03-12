package me.sflorian.chip8.befehle.helfer;

public enum Bedingung {
    GLEICH("SE"), UNGLEICH("SNE");

    private final String name;

    public String nameGeben() {
        return name;
    }

    Bedingung(String name) {
        this.name = name;
    }
}
