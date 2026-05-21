package todoapp.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Großer weißer Titel für den blauen Header ganz oben im Fenster.
 * SwingConstants.CENTER = zentriert (hab ich extra nachgeschaut).
 */
class TitelLabel extends JLabel {

    TitelLabel(String text) {
        super(text, SwingConstants.CENTER); // zweiter Parameter ist die Ausrichtung
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setForeground(Color.WHITE);
    }
}
