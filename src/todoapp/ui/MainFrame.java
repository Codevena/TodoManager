package todoapp.ui;

import todoapp.service.TodoService;

import javax.swing.*;
import java.awt.*;

/**
 * Das Hauptfenster der Anwendung.
 * JFrame ist quasi das Fenster selbst - das hab ich mir so gemerkt.
 */
@SuppressWarnings({ "serial", "this-escape" })
public class MainFrame extends JFrame {

    public MainFrame() {
        super(UIConstants.FENSTER_TITEL);
        initialize();
    }

    /**
     * initialize() ausgelagert damit kein "this-escape" Fehler kommt.
     * (hat die IDE gemeckert, Dozentin hat erklärt warum das ein Problem ist)
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setMinimumSize(new Dimension(600, 520));
        setLocationRelativeTo(null);
        add(erstelleHeader(), BorderLayout.NORTH);
        add(new TodoPanel(new TodoService()), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel erstelleHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppColors.BLAU_HEADER);
        header.setPreferredSize(new Dimension(0, 48));
        header.add(new TitelLabel(UIConstants.FENSTER_TITEL), BorderLayout.CENTER);
        return header;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fallback auf Standard-Look-and-Feel
            }
            new MainFrame();
        });
    }
}
