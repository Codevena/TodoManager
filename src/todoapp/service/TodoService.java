package todoapp.service;

import todoapp.model.Status;
import todoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hier ist die Logik die hinter dem UI steckt.
 * implements bedeutet dass wir alle Methoden aus dem Interface umsetzen müssen.
 */
public class TodoService implements TodoVerwaltbar {

    // ArrayList als interne Speicherliste - so ähnlich wie eine Python-Liste
    private final List<Todo> todos = new ArrayList<>();

    /**
     * neue Aufgabe hinzufügen - null-Check damit die App nicht crasht
     */
    @Override
    public void hinzufügen(Todo t) {
        if (t == null) {
            throw new IllegalArgumentException("Aufgabe darf nicht null sein.");
        }
        todos.add(t);
    }

    /**
     * Aufgabe löschen anhand der ID.
     * removeIf ist praktisch - gibt true zurück wenn was gelöscht wurde.
     */
    @Override
    public void löschen(int id) {
        boolean entfernt = todos.removeIf(t -> t.getId() == id);
        if (!entfernt) {
            throw new IllegalArgumentException("Keine Aufgabe mit ID " + id + " gefunden.");
        }
    }

    /**
     * geänderte Aufgabe speichern - sucht das alte Objekt und ersetzt es
     */
    @Override
    public void bearbeiten(Todo t) {
        if (t == null) {
            throw new IllegalArgumentException("Aufgabe darf nicht null sein.");
        }
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == t.getId()) {
                todos.set(i, t); // altes Objekt an Stelle i ersetzen
                return;
            }
        }
        throw new IllegalArgumentException("Keine Aufgabe mit ID " + t.getId() + " gefunden.");
    }

    /**
     * gibt eine Kopie der Liste zurück (new ArrayList<>) damit niemand von außen die interne Liste verändern kann
     */
    @Override
    public List<Todo> getAlle() {
        return new ArrayList<>(todos);
    }

    /**
     * filtert nach Status mit Stream - ähnlich wie list comprehension in Python
     */
    @Override
    public List<Todo> nachStatus(Status s) {
        return todos.stream()
                .filter(t -> t.getStatus() == s)
                .collect(Collectors.toList());
    }
}
