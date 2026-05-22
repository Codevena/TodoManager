# TodoManager

Eine Todo-Verwaltung als Java-Swing-Desktop-Anwendung.

## Aufbau

```
src/todoapp/
├── model/      # Todo, Status, Priorität
├── service/    # TodoService, TodoVerwaltbar, TodoSpeicher (JSON-Speicherung)
└── ui/         # Swing-Oberfläche (MainFrame, Panels, Widgets)
lib/            # externe Libraries (gson für JSON)
```

## Speicherung

Die Aufgaben werden als JSON in einer Datei `todos.json` (im Startordner) gespeichert
und beim nächsten Start automatisch wieder geladen. Dafür nutze ich die Library
[Gson](https://github.com/google/gson), die im Ordner `lib/` liegt.

## Bauen & Starten

Wichtig: die Gson-`.jar` muss mit in den Classpath (`-cp`), sonst findet Java die
Library nicht.

```bash
# Kompilieren
javac -encoding UTF-8 -cp lib/gson-2.11.0.jar -d out $(find src -name "*.java")

# Starten (unter Windows ist der Trenner im Classpath ; statt :)
java -cp out:lib/gson-2.11.0.jar todoapp.ui.MainFrame
```

## Anforderungen

- Java 17 oder neuer
