package todoapp.service;

import todoapp.model.Status;
import todoapp.model.Todo;

import java.util.List;

/**
 * Interface für die Todo-Verwaltung.
 * Ein Interface ist wie ein Vertrag - wer es implementiert muss alle Methoden schreiben.
 * (in Python gibt's so was nicht wirklich, closest thing wäre eine abstrakte Klasse)
 */
public interface TodoVerwaltbar {

    /**
     * neue Aufgabe hinzufügen
     */
    void hinzufügen(Todo t);

    /**
     * Aufgabe anhand der ID löschen
     */
    void löschen(int id);

    /**
     * bestehende Aufgabe aktualisieren (ersetzt das Objekt in der Liste)
     */
    void bearbeiten(Todo t);

    /**
     * alle Aufgaben zurückgeben
     */
    List<Todo> getAlle();

    /**
     * nach Status filtern (wird für spätere Funktionen nützlich sein)
     */
    List<Todo> nachStatus(Status s);
}
