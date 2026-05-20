package todoapp.ui;

import javax.swing.*;
import java.awt.*;

// Eingabefeld mit einheitlichem Look - extends JTextField
// Der doppelte Border-Trick war von der Dozentin: erst Linie, dann Innenabstand
class EingabeFeld extends JTextField {

    EingabeFeld() {
        setFont(new Font("SansSerif", Font.PLAIN, 13));
        // CompoundBorder = zwei Border ineinander (äußere Linie + innerer Abstand)
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRAU_RAND),
                BorderFactory.createEmptyBorder(5, 8, 5, 8) // oben, links, unten, rechts
        ));
        setPreferredSize(new Dimension(0, 32)); // Breite 0 = nimmt den verfügbaren Platz
    }
}
