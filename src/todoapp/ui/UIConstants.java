package todoapp.ui;

// Hier sammle ich alle Texte die irgendwo angezeigt werden.
// So muss ich nicht überall im Code rumsuchen wenn sich was ändert.
// (in Python wäre das einfach ein dict gewesen lol)
final class UIConstants {

    // Fenstertitel
    static final String FENSTER_TITEL = "TodoManager";

    // Die Beschriftungen im Eingabe-Formular
    static final String EINGABE_HEADER = "Neue Aufgabe erstellen";
    static final String TITEL_LABEL = "Titel:";
    static final String BESCHR_LABEL = "Beschreibung:";
    static final String PRIO_LABEL = "Priorität:";
    static final String STATUS_LABEL_TEXT = "Status:";

    // Button-Texte
    static final String BTN_HINZUFUEGEN = "Hinzufügen";
    static final String BTN_LOESCHEN = "Löschen";
    static final String BTN_SPEICHERN = "Speichern";

    // Spaltenüberschriften in der Tabelle
    static final String SPALTE_ID = "#";
    static final String SPALTE_TITEL = "Titel";
    static final String SPALTE_PRIO = "Priorität";
    static final String SPALTE_STATUS = "Status";
    static final String SPALTE_AKTION = "Aktion";

    // Texte für die Popup-Dialoge (JOptionPane macht sowas in Java, in JS wäre es
    // alert())
    static final String DIALOG_LOESCHEN_MSG = "Aufgabe wirklich löschen?";
    static final String DIALOG_LOESCHEN_TITEL = "Löschen bestätigen";
    static final String DIALOG_KEIN_TITEL_MSG = "Bitte einen Titel eingeben.";
    static final String DIALOG_KEIN_TITEL = "Fehlende Eingabe";
    static final String DIALOG_KEINE_AUSWAHL = "Keine Auswahl";
    static final String DIALOG_AUSWAHL_MSG = "Bitte zuerst eine Aufgabe auswählen.";

    // privater Konstruktor damit man kein Objekt davon erstellen kann
    // (hab ich von der Dozentin so gelernt, macht Sinn)
    private UIConstants() {
    }
}
