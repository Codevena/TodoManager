package todoapp.model;

// Enum für den Status einer Aufgabe
public enum Status {
    OFFEN("Offen"),
    IN_BEARBEITUNG("In Bearbeitung"),
    ERLEDIGT("Erledigt"),
    ARCHIVIERT("Archiviert");

    private final String anzeigeText;

    Status(String anzeigeText) {
        this.anzeigeText = anzeigeText;
    }

    @Override
    public String toString() {
        return anzeigeText;
    }
}
