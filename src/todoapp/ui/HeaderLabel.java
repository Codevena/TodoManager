package todoapp.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Eigene Klasse für die Überschrift im Panel (z.B. "Neue Aufgabe erstellen")
// Extends JLabel bedeutet dass wir alles von JLabel kriegen + unsere Anpassungen
// (in JS wäre das class HeaderLabel extends JLabel gewesen - fast gleiche Syntax!)
class HeaderLabel extends JLabel {

    // Konstruktor - bekommt den Text und setzt Schrift + Abstand nach unten
    HeaderLabel(String text) {
        super(text); // ruft den JLabel-Konstruktor auf
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setBorder(new EmptyBorder(0, 0, 10, 0)); // oben, links, unten, rechts
    }
}
