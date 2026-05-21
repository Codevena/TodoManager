package todoapp.ui;

import todoapp.model.Priorität;
import todoapp.model.Status;
import todoapp.model.Todo;

import javax.swing.*;
import java.awt.*;

/**
 * Das Formular oben - hier gibt man Titel, Beschreibung, Priorität und Status ein.
 * Die Buttons sind auch hier drin.
 * Die eigentliche Logik (was passiert beim Klick) kommt von außen rein (per Setter).
 */
@SuppressWarnings("serial")
class EingabePanel extends JPanel {

    // ── Eingabe-Komponenten ───────────────────────────────────────────────
    private final EingabeFeld titelField;
    private final EingabeFeld beschreibungField;
    private final JComboBox<Priorität> prioritätBox;
    private final JComboBox<Status> statusBox;

    // die drei Buttons
    private final AktionsButton hinzufügenBtn;
    private final AktionsButton löschenBtn;
    private final AktionsButton speichernBtn;

    /**
     * Konstruktor - baut das ganze Panel zusammen
     */
    EingabePanel() {
        setLayout(new BorderLayout());
        setBackground(AppColors.WEISS);
        setBorder(erstelleRahmen());
        titelField = new EingabeFeld();
        beschreibungField = new EingabeFeld();
        prioritätBox = new JComboBox<>(Priorität.values());
        statusBox = new JComboBox<>(Status.values());
        stilisiereComboBoxen();
        hinzufügenBtn = new AktionsButton(UIConstants.BTN_HINZUFUEGEN, AppColors.BLAU_BTN);
        löschenBtn = new AktionsButton(UIConstants.BTN_LOESCHEN, AppColors.ROT_BTN);
        speichernBtn = new AktionsButton(UIConstants.BTN_SPEICHERN, AppColors.GRUEN_BTN);
        add(new HeaderLabel(UIConstants.EINGABE_HEADER), BorderLayout.NORTH);
        add(erstelleFelderPanel(), BorderLayout.CENTER);
        add(erstelleButtonLeiste(), BorderLayout.SOUTH);
    }

    // Hilfsmethoden für den Aufbau des Panels

    private static javax.swing.border.Border erstelleRahmen() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRAU_RAND, 1, true),
                BorderFactory.createEmptyBorder(12, 14, 12, 14));
    }

    private void stilisiereComboBoxen() {
        stilisiereComboBox(prioritätBox);
        stilisiereComboBox(statusBox);
    }

    private static void stilisiereComboBox(JComboBox<?> box) {
        box.setFont(new Font("SansSerif", Font.PLAIN, 13));
        box.setPreferredSize(new Dimension(0, 32));
        box.setBorder(BorderFactory.createLineBorder(AppColors.GRAU_RAND));
    }

    /**
     * die Felder (Titel + Beschreibung) kommen zuerst, dann Priorität und Status
     * nebeneinander
     */
    private JPanel erstelleFelderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppColors.WEISS);
        GridBagConstraints c = erstelleConstraints();
        fuelleObereFelder(panel, c);
        fuelleUntereFelder(panel, c);
        return panel;
    }

    private static GridBagConstraints erstelleConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 0, 4, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        return c;
    }

    private void fuelleObereFelder(JPanel p, GridBagConstraints c) {
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        p.add(new EtikettLabel(UIConstants.TITEL_LABEL), c);
        c.gridy = 1;
        p.add(titelField, c);
        c.gridy = 2;
        p.add(new EtikettLabel(UIConstants.BESCHR_LABEL), c);
        c.gridy = 3;
        p.add(beschreibungField, c);
    }

    private void fuelleUntereFelder(JPanel p, GridBagConstraints c) {
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        p.add(new EtikettLabel(UIConstants.PRIO_LABEL), c);
        c.gridx = 1;
        p.add(new EtikettLabel(UIConstants.STATUS_LABEL_TEXT), c);
        c.gridx = 0;
        c.gridy = 5;
        p.add(prioritätBox, c);
        c.gridx = 1;
        p.add(statusBox, c);
    }

    private JPanel erstelleButtonLeiste() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panel.setBackground(AppColors.WEISS);
        panel.add(hinzufügenBtn);
        panel.add(löschenBtn);
        panel.add(speichernBtn);
        return panel;
    }

    /**
     * gibt den eingetippten Titel zurück (trim() entfernt Leerzeichen am Rand)
     */
    String getTitel() {
        return titelField.getText().trim();
    }

    /**
     * dasselbe für die Beschreibung
     */
    String getBeschreibung() {
        return beschreibungField.getText().trim();
    }

    /**
     * gibt den ausgewählten Enum-Wert aus der Dropdown zurück
     */
    Priorität getPriorität() {
        return (Priorität) prioritätBox.getSelectedItem();
    }

    /**
     * dasselbe für den Status
     */
    Status getStatus() {
        return (Status) statusBox.getSelectedItem();
    }

    /**
     * befüllt die Felder wenn man eine Aufgabe in der Tabelle anklickt
     * (Bearbeiten-Modus)
     */
    void befüllen(Todo t) {
        titelField.setText(t.getTitel());
        beschreibungField.setText(t.getBeschreibung());
        prioritätBox.setSelectedItem(t.getPriorität());
        statusBox.setSelectedItem(t.getStatus());
    }

    /**
     * setzt alle Felder wieder leer
     */
    void leeren() {
        titelField.setText("");
        beschreibungField.setText("");
        prioritätBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
    }

    /**
     * hier kommt die Logik von TodoPanel rein (Lambda/Methodenreferenz)
     */
    void setHinzufügenAction(Runnable action) {
        hinzufügenBtn.addActionListener(e -> action.run());
    }

    /**
     * gleiches Muster wie oben
     */
    void setLöschenAction(Runnable action) {
        löschenBtn.addActionListener(e -> action.run());
    }

    /**
     * gleiches Muster
     */
    void setSpeichernAction(Runnable action) {
        speichernBtn.addActionListener(e -> action.run());
    }
}
