package todoapp.ui;

import javax.swing.*;
import java.awt.*;

// Kleines Label für Beschriftungen vor den Eingabefeldern ("Titel:", "Status:" usw.)
// Grauere Farbe damit man es von den Feldern unterscheiden kann
class EtikettLabel extends JLabel {

    EtikettLabel(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.PLAIN, 12)); // etwas kleiner als der Header
        setForeground(AppColors.TEXT_NORMAL); // grau aus AppColors
    }
}
