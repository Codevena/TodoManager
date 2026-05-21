package todoapp.model;

/**
 * Enum für die Priorität einer Aufgabe (HOCH, MITTEL, NIEDRIG).
 * toString() ist überschrieben damit in der UI schöne Texte angezeigt werden.
 */
public enum Priorität {
    HOCH("Hoch"),
    MITTEL("Mittel"),
    NIEDRIG("Niedrig");

    private final String anzeigeText;

    Priorität(String anzeigeText) {
        this.anzeigeText = anzeigeText;
    }

    @Override
    public String toString() {
        return anzeigeText;
    }
}
