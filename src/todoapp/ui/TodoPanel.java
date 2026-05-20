package todoapp.ui;

import todoapp.model.Status;
import todoapp.model.Todo;
import todoapp.service.TodoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Das Panel in der Mitte - verbindet Formular, Tabelle und Statuszeile
// Hier ist die eigentliche Logik: was passiert beim Klick auf die Buttons
@SuppressWarnings("serial")
public class TodoPanel extends JPanel {

    // transient weil JPanel Serializable ist, TodoService aber nicht
    // (hab ich ehrlich gesagt noch nicht ganz verstanden aber so geht's)
    private transient final TodoService service;

    // die drei Sub-Komponenten als Felder damit alle Methoden drauf zugreifen können
    private EingabePanel eingabe;
    private TodoTabelle  tabelle;
    private StatusLabel  statusLabel;

    // ── Konstruktor ──────────────────────────────────────────────────────

    public TodoPanel(TodoService service) {
        this.service = service;
        initialize();
    }

    // initialize() ausgelagert (gleiches Muster wie in MainFrame)
    private void initialize() {
        konfigurierePanelAussehen();
        erstelleSubkomponenten();
        verdrahteListener();
        bauePanelAuf();
        aktualisiereTabelle();
    }

    private void konfigurierePanelAussehen() {
        setLayout(new BorderLayout(10, 10));
        setBackground(AppColors.HINTERGRUND);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void erstelleSubkomponenten() {
        eingabe     = new EingabePanel();
        tabelle     = new TodoTabelle();
        statusLabel = new StatusLabel();
    }

    // die Buttons mit den Methoden verbinden
    // Methodenreferenzen (this::hinzufügenAction) sind wie Arrow-Functions in JS
    private void verdrahteListener() {
        eingabe.setHinzufügenAction(this::hinzufügenAction);
        eingabe.setLöschenAction(this::löschenAction);
        eingabe.setSpeichernAction(this::speichernAction);
        tabelle.addAuswahlListener(this::zeilenAuswahl);
    }

    // fügt die Sub-Komponenten an die richtigen Stellen ein (Nord/Mitte/Süd)
    private void bauePanelAuf() {
        add(eingabe,              BorderLayout.NORTH);
        add(tabelle,              BorderLayout.CENTER);
        add(erstelleStatusZeile(), BorderLayout.SOUTH);
    }

    // ── Aktionen ──────────────────────────────────────────────────────────

    // wird aufgerufen wenn der Hinzufügen-Button geklickt wird
    private void hinzufügenAction() {
        if (!validiereEingabe()) return;
        Todo neu = new Todo(
                eingabe.getTitel(),
                eingabe.getBeschreibung(),
                eingabe.getPriorität(),
                eingabe.getStatus());
        service.hinzufügen(neu);
        zurücksetzen();
    }

    // Löschen mit Bestätigung (damit man nicht aus Versehen löscht)
    private void löschenAction() {
        int id = tabelle.getGewählteId();
        if (id < 0) {
            zeigeHinweis(UIConstants.DIALOG_AUSWAHL_MSG);
            return;
        }
        int antwort = JOptionPane.showConfirmDialog(this,
                UIConstants.DIALOG_LOESCHEN_MSG + " (#" + id + ")",
                UIConstants.DIALOG_LOESCHEN_TITEL, JOptionPane.YES_NO_OPTION);
        if (antwort == JOptionPane.YES_OPTION) {
            service.löschen(id);
            zurücksetzen();
        }
    }

    // Änderungen an einem bestehenden Todo speichern
    private void speichernAction() {
        int id = tabelle.getGewählteId();
        if (id < 0) {
            zeigeHinweis(UIConstants.DIALOG_AUSWAHL_MSG);
            return;
        }
        if (!validiereEingabe()) return;
        aktualisiereGewähltesObjekt(id);
        zurücksetzen();
    }

    // das eigentliche Updaten ausgelagert damit speichernAction() kürzer bleibt
    private void aktualisiereGewähltesObjekt(int id) {
        service.getAlle().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .ifPresent(t -> {
                    t.setTitel(eingabe.getTitel());
                    t.setBeschreibung(eingabe.getBeschreibung());
                    t.setPriorität(eingabe.getPriorität());
                    t.setStatus(eingabe.getStatus());
                    service.bearbeiten(t);
                });
    }

    // wenn man eine Zeile in der Tabelle anklickt werden die Felder befüllt
    private void zeilenAuswahl(int id) {
        if (id < 0) return;
        service.getAlle().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .ifPresent(eingabe::befüllen);
    }

    // ── Interne Hilfsmethoden ─────────────────────────────────────────────

    // Tabelle neu laden - public damit man es auch von außen aufrufen könnte
    public void aktualisiereTabelle() {
        List<Todo> alle = service.getAlle();
        tabelle.aktualisieren(alle);
        aktualisiereStatusZeile(alle);
    }

    private void aktualisiereStatusZeile(List<Todo> alle) {
        long offen    = alle.stream().filter(t -> t.getStatus() == Status.OFFEN).count();
        long erledigt = alle.stream().filter(t -> t.getStatus() == Status.ERLEDIGT).count();
        statusLabel.setText(alle.size() + " Aufgaben gesamt · " + offen + " offen · " + erledigt + " erledigt");
    }

    private void zurücksetzen() {
        eingabe.leeren();
        tabelle.auswahlAufheben();
        aktualisiereTabelle();
    }

    // prüft ob ein Titel eingegeben wurde (Pflichtfeld)
    private boolean validiereEingabe() {
        if (eingabe.getTitel().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    UIConstants.DIALOG_KEIN_TITEL_MSG,
                    UIConstants.DIALOG_KEIN_TITEL,
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void zeigeHinweis(String nachricht) {
        JOptionPane.showMessageDialog(this, nachricht,
                UIConstants.DIALOG_KEINE_AUSWAHL, JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Status-Zeile ──────────────────────────────────────────────────────

    private JPanel erstelleStatusZeile() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(AppColors.STATUS_BG);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppColors.GRAU_RAND));
        panel.add(statusLabel);
        return panel;
    }
}
