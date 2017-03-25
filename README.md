# java-chip8-emu
Ein einfacher [CHIP-8](https://de.wikipedia.org/wiki/CHIP-8) Emulator, geschrieben in Java.

## Erstellen des Projekts
Das Projekt kann ganz einfach mit Hilfe von Gradle erstellt werden. Wenn Gradle nicht installiert ist, kann `gradlew.bat`/`gradlew.sh` als Ersatz verwendet werden:
```
gradlew build
```
Wenn es installiert ist:
```
gradle build
```
Die vollständige ausführbare *.jar-Datei befindet sich dann im Anschluss in `build/libs`.

## Ausführen einer CHIP-8 ROM
Um eine CHIP-8 ROM (`*.ch8`, `*.chip8` wird anerkannt) auszuführen, kann die Datei als Programmargument angegeben werden:
```
java -jar java-chip8-emu.jar <dateipfad>
```
Alternativ kann die jar Datei ohne Argumente ausgeführt werden, woraufhin ein Dateibrowser geöffnet wird.