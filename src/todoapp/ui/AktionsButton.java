package todoapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Button-Klasse die ich für alle drei Buttons nutze (Hinzufügen, Löschen, Speichern).
 * Jeder bekommt eine andere Farbe, der Rest ist gleich.
 */
class AktionsButton extends JButton {

    /**
     * bekommt den Text und die Farbe von außen
     */
    AktionsButton(String text, Color hintergrund) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 13));
        setBackground(hintergrund);
        setForeground(AppColors.WEISS); // weißer Text
        setFocusPainted(false);  // keinen hässlichen Fokus-Rahmen
        setBorderPainted(false); // keinen Standard-Rahmen
        setOpaque(true); // ohne das ignoriert Swing die Hintergrundfarbe manchmal?!
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mauszeiger als Hand
        setPreferredSize(new Dimension(130, 36));
        registriereHoverEffekt(hintergrund);
    }

    /**
     * Hover-Effekt: Farbe wird heller wenn man drüber fährt.
     * MouseAdapter ist wie ein EventListener in JS.
     */
    private void registriereHoverEffekt(Color normal) {
        Color heller = normal.brighter(); // brighter() macht die Farbe automatisch heller
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { setBackground(heller); }
            @Override public void mouseExited (MouseEvent e) { setBackground(normal); }
        });
    }
}
