package todoapp.ui;

import todoapp.model.Priorität;
import todoapp.model.Status;
import todoapp.model.Todo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

// Die Tabelle die alle Aufgaben anzeigt
// JPanel als äußere Hülle damit wir BorderLayout nutzen können (direkt in JTable geht das nicht so einfach)
@SuppressWarnings("serial")
class TodoTabelle extends JPanel {

    // ── Spalten-Indizes ───────────────────────────────────────────────────
    private static final int SPALTE_ID = 0;
    private static final int SPALTE_TITEL = 1;
    private static final int SPALTE_PRIORITÄT = 2;
    private static final int SPALTE_STATUS = 3;
    private static final int SPALTE_AKTION = 4;

    private static final String[] SPALTEN = {
            UIConstants.SPALTE_ID,
            UIConstants.SPALTE_TITEL,
            UIConstants.SPALTE_PRIO,
            UIConstants.SPALTE_STATUS,
            UIConstants.SPALTE_AKTION
    };

    // ── Komponenten ───────────────────────────────────────────────────────
    private final JTable tabelle;
    private final DefaultTableModel modell;

    // ── Konstruktor ───────────────────────────────────────────────────────

    TodoTabelle() {
        setLayout(new BorderLayout());
        modell = erstelleModell();
        tabelle = new JTable(modell);
        konfiguriereTabellenAussehen();
        konfiguriereRenderer();
        konfiguriereKopfzeile();
        add(erstelleScrollPane(), BorderLayout.CENTER);
    }

    private static DefaultTableModel erstelleModell() {
        return new DefaultTableModel(SPALTEN, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
    }

    private JScrollPane erstelleScrollPane() {
        JScrollPane sp = new JScrollPane(tabelle);
        sp.setBorder(BorderFactory.createLineBorder(AppColors.GRAU_RAND, 1));
        return sp;
    }

    // ── Konfiguration ─────────────────────────────────────────────────────

    private void konfiguriereTabellenAussehen() {
        tabelle.setRowHeight(36);
        tabelle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabelle.setShowGrid(false);
        tabelle.setIntercellSpacing(new Dimension(0, 0));
        tabelle.setSelectionBackground(AppColors.AUSWAHL_BG);
        tabelle.setSelectionForeground(Color.BLACK);
        tabelle.setFocusable(false);
        konfiguriereSpaltenwbreiten();
    }

    private void konfiguriereSpaltenwbreiten() {
        tabelle.getColumnModel().getColumn(SPALTE_ID).setPreferredWidth(40);
        tabelle.getColumnModel().getColumn(SPALTE_TITEL).setPreferredWidth(220);
        tabelle.getColumnModel().getColumn(SPALTE_PRIORITÄT).setPreferredWidth(100);
        tabelle.getColumnModel().getColumn(SPALTE_STATUS).setPreferredWidth(120);
        tabelle.getColumnModel().getColumn(SPALTE_AKTION).setPreferredWidth(70);
    }

    private void konfiguriereKopfzeile() {
        tabelle.getTableHeader().setBackground(AppColors.TABELLE_HEAD);
        tabelle.getTableHeader().setForeground(AppColors.WEISS);
        tabelle.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabelle.getTableHeader().setReorderingAllowed(false);
    }

    private void konfiguriereRenderer() {
        tabelle.getColumnModel().getColumn(SPALTE_PRIORITÄT).setCellRenderer(new BadgeRenderer(true));
        tabelle.getColumnModel().getColumn(SPALTE_STATUS).setCellRenderer(new BadgeRenderer(false));
        tabelle.getColumnModel().getColumn(SPALTE_AKTION).setCellRenderer(new AktionRenderer());
    }

    // Tabelle neu befüllen - erst leeren dann alle Todos einfügen
    void aktualisieren(List<Todo> todos) {
        modell.setRowCount(0);
        for (Todo t : todos) {
            modell.addRow(new Object[] {
                    t.getId(), t.getTitel(), t.getPriorität(), t.getStatus(), "✎ ✕"
            });
        }
    }

    // gibt die ID der ausgewählten Zeile zurück (-1 wenn nichts ausgewählt)
    int getGewählteId() {
        int zeile = tabelle.getSelectedRow();
        if (zeile < 0)
            return -1;
        return (int) modell.getValueAt(zeile, SPALTE_ID);
    }

    // Auswahl aufheben (nach Hinzufügen/Löschen/Speichern)
    void auswahlAufheben() {
        tabelle.clearSelection();
    }

    // Listener registrieren für Klicks auf eine Zeile
    // Consumer<Integer> ist wie eine Funktion die eine Zahl bekommt (kein
    // Rückgabewert)
    void addAuswahlListener(Consumer<Integer> listener) {
        tabelle.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                listener.accept(getGewählteId());
            }
        });
    }

    // Innere Klassen für die Renderer (wie man Zellen individuell gestaltet)
    // war am Anfang verwirrend aber macht jetzt mehr Sinn

    // Badge-Renderer: zeichnet farbige Labels für Priorität und Status
    // musste paintComponent überschreiben damit die abgerundeten Ecken
    // funktionieren
    @SuppressWarnings("serial")
    private static class BadgeRenderer extends DefaultTableCellRenderer {

        private Color badgeFarbe = null;

        BadgeRenderer(boolean istPriorität) {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            setOpaque(true);
            setText(value == null ? "" : value.toString());
            setFont(new Font("SansSerif", Font.BOLD, 12));
            setBorder(BorderFactory.createEmptyBorder());
            badgeFarbe = bestimmeFarbe(value);
            setBackground(zeileHintergrund(isSelected, row));
            setForeground(Color.BLACK);
            return this;
        }

        private static Color zeileHintergrund(boolean isSelected, int row) {
            if (isSelected)
                return AppColors.AUSWAHL_BG;
            return row % 2 == 0 ? AppColors.WEISS : AppColors.ZEILE_ALT;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            if (badgeFarbe != null) {
                zeichneBadge(g2);
            }
            g2.dispose();
        }

        private void zeichneBadge(Graphics2D g2) {
            int bw = Math.min(getWidth() - 16, 78);
            int bh = 22;
            int bx = (getWidth() - bw) / 2;
            int by = (getHeight() - bh) / 2;
            g2.setColor(badgeFarbe);
            g2.fillRoundRect(bx, by, bw, bh, 12, 12);
            zeichneBadgeText(g2, bx, by, bw, bh);
        }

        private void zeichneBadgeText(Graphics2D g2, int bx, int by, int bw, int bh) {
            g2.setColor(AppColors.WEISS);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontMetrics fm = g2.getFontMetrics();
            String txt = getText();
            int tx = bx + (bw - fm.stringWidth(txt)) / 2;
            int ty = by + (bh + fm.getAscent() - fm.getDescent()) / 2 - 1;
            g2.drawString(txt, tx, ty);
        }

        private static Color bestimmeFarbe(Object value) {
            if (value instanceof Priorität p) {
                return switch (p) {
                    case HOCH -> AppColors.BADGE_HOCH;
                    case MITTEL -> AppColors.BADGE_MITTEL;
                    case NIEDRIG -> AppColors.BADGE_NIEDRIG;
                };
            } else if (value instanceof Status s) {
                return switch (s) {
                    case OFFEN -> AppColors.BADGE_OFFEN;
                    case IN_BEARBEITUNG -> AppColors.BADGE_IN_BEARBEITUNG;
                    case ERLEDIGT -> AppColors.BADGE_ERLEDIGT;
                    case ARCHIVIERT -> AppColors.BADGE_ARCHIVIERT;
                };
            }
            return null;
        }
    }

    // Renderer für die Aktion-Spalte (die Stift- und X-Icons)
    @SuppressWarnings("serial")
    private static class AktionRenderer extends DefaultTableCellRenderer {

        AktionRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 6));
            panel.setBackground(zeileHintergrund(isSelected, row));
            panel.add(erstelleIconLabel("✎", new Color(100, 100, 100)));
            panel.add(erstelleIconLabel("✕", new Color(180, 60, 60)));
            return panel;
        }

        private static Color zeileHintergrund(boolean isSelected, int row) {
            if (isSelected)
                return AppColors.AUSWAHL_BG;
            return row % 2 == 0 ? AppColors.WEISS : AppColors.ZEILE_ALT;
        }

        private static JLabel erstelleIconLabel(String symbol, Color farbe) {
            JLabel label = new JLabel(symbol);
            label.setForeground(farbe);
            label.setFont(new Font("SansSerif", Font.PLAIN, 15));
            return label;
        }
    }
}
