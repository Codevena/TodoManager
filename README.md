# TodoManager

Eine Todo-Verwaltung als Java-Swing-Desktop-Anwendung.

## Aufbau

```
src/todoapp/
├── model/      # Todo, Status, Priorität
├── service/    # TodoService, TodoVerwaltbar
└── ui/         # Swing-Oberfläche (MainFrame, Panels, Widgets)
```

## Bauen & Starten

```bash
# Kompilieren
javac -d out $(find src -name "*.java")

# Starten
java -cp out todoapp.ui.MainFrame
```

## Anforderungen

- Java 17 oder neuer
