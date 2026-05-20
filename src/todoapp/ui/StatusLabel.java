package todoapp.ui;

import javax.swing.*;
import java.awt.*;

// Das Label ganz unten das anzeigt wie viele Aufgaben es gibt
// kein Text im Konstruktor weil der Text später dynamisch gesetzt wird
class StatusLabel extends JLabel {

    StatusLabel() {
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setForeground(AppColors.TEXT_GRAU); // etwas heller als normaler Text
    }
}
