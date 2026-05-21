package todoapp.model;

import java.time.LocalDate;

/**
 * Das ist die Klasse für eine einzelne Aufgabe.
 * In Python wäre das einfach ein dict gewesen, aber in Java macht man dafür eine eigene Klasse.
 */
public class Todo {

    // Zähler der automatisch hochzählt damit jede Aufgabe eine eindeutige ID
    // bekommt
    // static bedeutet dass alle Todo-Objekte denselben Zähler teilen
    private static int idZaehler = 1;

    private final int id; // wird im Konstruktor gesetzt und kann nicht mehr geändert werden
    private String titel;
    private String beschreibung;
    private Priorität priorität; // Enum-Typ
    private Status status; // auch ein Enum
    private final LocalDate erstelltAm; // Datum wird automatisch gesetzt

    /**
     * Konstruktor - bekommt alle Werte von außen, ID wird automatisch vergeben.
     */
    public Todo(String titel, String beschreibung, Priorität priorität, Status status) {
        this.id = idZaehler++; // ++ erhöht den Zähler nach jeder Zuweisung
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.priorität = priorität;
        this.status = status;
        this.erstelltAm = LocalDate.now(); // aktuelles Datum
    }

    // Getter - geben die Werte nach außen, die Felder selbst sind private

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public Priorität getPriorität() {
        return priorität;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getErstelltAm() {
        return erstelltAm;
    }

    // Setter - damit kann man Werte später ändern (außer id und erstelltAm, die
    // sind final)
    // (id und erstelltAm haben keinen Setter weil die sich nicht ändern sollen)

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setPriorität(Priorität priorität) {
        this.priorität = priorität;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + titel + " (" + priorität + " | " + status + ")";
    }
}
