package todoapp.ui;

import java.awt.Color;

// Alle Farben die ich in der App verwende sind hier gesammelt
// So muss ich Farben nur einmal definieren und kann sie überall referenzieren
// (gleiche Idee wie CSS-Variablen, nur halt in Java)
final class AppColors {

    // allgemeine Hintergrundfarben
    static final Color HINTERGRUND  = new Color(245, 246, 248); // fast weiß
    static final Color WEISS        = Color.WHITE;
    static final Color GRAU_RAND    = new Color(200, 200, 200); // für Rähmen
    static final Color ZEILE_ALT    = new Color(249, 250, 252); // leicht grau für jede zweite Zeile
    static final Color AUSWAHL_BG   = new Color(225, 238, 255); // hell-blau wenn man was markiert

    // Farbe für den Header oben und den Tabellenkopf (gleiche Farbe)
    static final Color BLAU_HEADER  = new Color(70, 130, 180);
    static final Color TABELLE_HEAD = new Color(70, 130, 180);

    // Button-Farben (Hinzufügen=blau, Löschen=rot, Speichern=grün)
    static final Color BLAU_BTN  = new Color(52, 144, 220);
    static final Color ROT_BTN   = new Color(220, 53, 69);
    static final Color GRUEN_BTN = new Color(50, 168, 82);

    // Badge-Farben für Priorität (die farbigen Labels in der Tabelle)
    static final Color BADGE_HOCH    = new Color(220, 53, 69);  // rot
    static final Color BADGE_MITTEL  = new Color(255, 140, 0);  // orange
    static final Color BADGE_NIEDRIG = new Color(50, 168, 82);  // grün

    // Badge-Farben für Status
    static final Color BADGE_OFFEN          = new Color(255, 140, 0);  // orange
    static final Color BADGE_IN_BEARBEITUNG = new Color(52, 144, 220); // blau
    static final Color BADGE_ERLEDIGT       = new Color(50, 168, 82);  // grün
    static final Color BADGE_ARCHIVIERT     = new Color(120, 120, 120); // grau

    // Text-Farben
    static final Color TEXT_NORMAL = new Color(80, 80, 80);  // dunkelgrau
    static final Color TEXT_GRAU   = new Color(100, 100, 100); // heller

    // Hintergrund der Statuszeile ganz unten
    static final Color STATUS_BG = new Color(238, 240, 243);

    // privater Konstruktor damit man kein Objekt erstellen kann (ist eine Util-Klasse)
    private AppColors() {}
}
