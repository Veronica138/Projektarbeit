package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Reiseplanung extends JFrame {
    private javax.swing.JPanel JPanel;
    private javax.swing.JLabel JLabel;
    private JComboBox cBoxUrlaubsort; // Urlaubsort auswählbar (top10)
    private JLabel JLabelDauer;
    private JLabel JLabelWohnart;
    private JTextField txtFieldDauer;
    private JComboBox cBoxWohnart; // Wohnart auswählbar
    private JLabel JLabelVerpflegung;
    private JComboBox cBoxAllinclusive;
    private JLabel JLabelAnzahlPerson;
    private JLabel JLabelSonderwünsche; //
    private JCheckBox SPABereich; // Ein Sonderwunsch für Label Sonderwünsche
    private JCheckBox pool; // Ein Sonderwunsch für Label Sonderwünsche
    private JCheckBox meerblick; // Ein Sonderwunsch für Label Sonderwünsche
    private JCheckBox terasse; // Ein Sonderwunsch für Label Sonderwünsche
    private JTextField txtFieldAnzahlPersonen; //Textfeld um Personenanzahl anzugeben
    private JButton preisBerechnungButton; // Button um Preis für konfigurierte Reise auszurechnen
    private JButton buttonSpeichern; // Button zur Speicherung der akutell konfigurierten Reise
    private JButton buttonReset;
    private JTextArea txtAreaReisespeichern; // Neue TextArea für die Liste
    private JButton buttonListeLoeschen; // Button zum Löschen der Liste
    private JLabel errorLabelPersonen; // Label um Fehler in Zeile Personen anzuzeigen
    private JLabel errorLabelDauer; // Label um Fehler in Zeile Dauer anzuzeigen

    // ArrayList zum Speichern der Reisen
    private ArrayList<String> reiseListe = new ArrayList<>();


    public Reiseplanung() {

        setTitle("Reiseplanung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setContentPane(JPanel);
        setVisible(true);

        // Fehlerlabels standardmäßig unsichtbar setzen
        errorLabelDauer.setVisible(false);
        errorLabelPersonen.setVisible(false);


        // Listener für Preisberechnung
        preisBerechnungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                berechneUndZeigePreis(); // Methode zur Berechnung und Anzeige
            }
        });

        // Button-Listener für "Reise speichern"
        buttonSpeichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speichereReise();
            }
        });

        // Button-Listener für "Liste löschen"
        buttonListeLoeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiseListe.clear(); // Liste leeren
                txtAreaReisespeichern.setText(""); // TextArea leeren
            }
        });

        // **BEGINN: Listener für "Reset"-Button**
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Textfelder zurücksetzen
                txtFieldDauer.setText("");
                txtFieldAnzahlPersonen.setText("");

                // ComboBoxen auf den Standardwert setzen (ersten Eintrag)
                if (cBoxUrlaubsort.getItemCount() > 0) {
                    cBoxUrlaubsort.setSelectedIndex(0);
                }
                if (cBoxWohnart.getItemCount() > 0) {
                    cBoxWohnart.setSelectedIndex(0);
                }
                if (cBoxAllinclusive.getItemCount() > 0) {
                    cBoxAllinclusive.setSelectedIndex(0);
                }

                // CheckBoxen deaktivieren
                SPABereich.setSelected(false);
                pool.setSelected(false);
                meerblick.setSelected(false);
                terasse.setSelected(false);

                // Fehlerlabels verbergen
                errorLabelDauer.setVisible(false);
                errorLabelPersonen.setVisible(false);

            }
        });
        // **ENDE: Listener für "Reset"-Button**
    }


    // Methode zur Preisberechnung und Anzeige
    private void berechneUndZeigePreis() {
        // Variablen zur Fehlererfassung
        boolean fehlerInDauer = false;
        boolean fehlerInPersonen = false;

        // Dauer überprüfen
        String dauerText = txtFieldDauer.getText();
        if (!dauerText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
            fehlerInDauer = true;
            errorLabelDauer.setVisible(true); // Fehlerlabel für Dauer anzeigen
        } else {
            errorLabelDauer.setVisible(false); // Änderung: Fehlerlabel bei korrekter Eingabe unsichtbar machen
        }

        // Personenzahl überprüfen
        String personenText = txtFieldAnzahlPersonen.getText();
        if (!personenText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
            fehlerInPersonen = true;
            errorLabelPersonen.setVisible(true); // Fehlerlabel für Personenanzahl anzeigen
        } else {
            errorLabelPersonen.setVisible(false); // Änderung: Fehlerlabel bei korrekter Eingabe unsichtbar machen
        }

        // Wenn Fehler aufgetreten sind, entsprechende Nachricht anzeigen
        if (fehlerInDauer && fehlerInPersonen) {
            JOptionPane.showMessageDialog(this,
                    "Bitte geben Sie gültige Zahlen für die Dauer und die Personenanzahl ein.",
                    "Ungültige Eingaben", JOptionPane.WARNING_MESSAGE);
            return; // Methode verlassen
        } else if (fehlerInDauer) {
            JOptionPane.showMessageDialog(this,
                    "Bitte geben Sie eine gültige ganze Zahl für die Dauer ein.",
                    "Ungültige Eingabe für Dauer", JOptionPane.WARNING_MESSAGE);
            return; // Methode verlassen
        } else if (fehlerInPersonen) {
            JOptionPane.showMessageDialog(this,
                    "Bitte geben Sie eine gültige ganze Zahl für die Personenanzahl ein.",
                    "Ungültige Eingabe für Personenanzahl", JOptionPane.WARNING_MESSAGE);
            return; // Methode verlassen
        }

        // Wenn keine Fehler aufgetreten sind, Informationen verarbeiten
        try {
            String wohnart = (String) cBoxWohnart.getSelectedItem();
            String allInclusive = (String) cBoxAllinclusive.getSelectedItem();
            int dauer = Integer.parseInt(txtFieldDauer.getText());
            int personen = Integer.parseInt(txtFieldAnzahlPersonen.getText());

            // Berechnung des Preises
            double preis = berechnePreis(dauer, personen, wohnart, allInclusive);

            // Zeige den berechneten Preis in einem Dialogfeld
            JOptionPane.showMessageDialog(this, "Ihre ausgewählte Reise koset " + String.format("%.2f €", preis),
                    "Preisberechnung", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für Dauer und Personenanzahl ein.",
                    "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
        }
    }



    // Methode zur Berechnung des Preises
    private double berechnePreis(int dauer, int personen, String wohnart, String allInclusive) {
        double grundpreisProTag = 50.0; // Basispreis pro Tag

        // Zuschläge für Wohnart
        switch (wohnart) {
            case "Standard":
                grundpreisProTag += 0;
                break;
            case "Deluxe":
                grundpreisProTag += 30;
                break;
            case "Suite":
                grundpreisProTag += 50;
                break;
            case "Penthouse":
                grundpreisProTag += 100;
                break;
        }

        // Zuschläge für All-Inclusive
        if (allInclusive.equals("Ja")) {
            grundpreisProTag += 20;
        }

        // Gesamtpreis berechnen
        return grundpreisProTag * dauer * personen;
    }


    // Methode zum Speichern der Reise in die Liste
    private void speichereReise() {
        // Fehleranzeigen zurücksetzen
        errorLabelDauer.setVisible(false);
        errorLabelPersonen.setVisible(false);

        try {
            // Dauer überprüfen
            String dauerText = txtFieldDauer.getText();
            if (!dauerText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
                errorLabelDauer.setVisible(true); // Fehlerlabel für Dauer anzeigen
                JOptionPane.showMessageDialog(this,
                        "Bitte geben Sie eine gültige ganze Zahl für die Dauer ein.",
                        "Ungültige Eingabe für Dauer", JOptionPane.WARNING_MESSAGE);
                return; // Methode verlassen
            }
            int dauer = Integer.parseInt(dauerText);

            // Personenzahl überprüfen
            String personenText = txtFieldAnzahlPersonen.getText();
            if (!personenText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
                errorLabelPersonen.setVisible(true); // Fehlerlabel für Personenanzahl anzeigen
                JOptionPane.showMessageDialog(this,
                        "Bitte geben Sie eine gültige ganze Zahl für die Personenanzahl ein.",
                        "Ungültige Eingabe für Personenanzahl", JOptionPane.WARNING_MESSAGE);
                return; // Methode verlassen
            }
            int personen = Integer.parseInt(personenText);

            // Informationen für die Reise sammeln
            String urlaubsort = (String) cBoxUrlaubsort.getSelectedItem();
            String wohnart = (String) cBoxWohnart.getSelectedItem();
            String allInclusive = (String) cBoxAllinclusive.getSelectedItem();
            double preis = berechnePreis(dauer, personen, wohnart, allInclusive);

            // Reiseinformationen zusammenstellen, sodass alle Zeilen linksbündig sind
            String reise = String.format(
                    "Urlaubsziel:\n" +
                            "Ort: %s\n" +
                            "Wohnart: %s\n" +
                            "Verpflegung: %s\n" +
                            "Dauer: %d Tage\n" +
                            "Personen: %d\n" +
                            "Preis: %.2f €",
                    urlaubsort, wohnart, allInclusive, dauer, personen, preis);


            // Reiseinformationen in die Liste speichern
            reiseListe.add(reise);

            // TextArea aktualisieren
            txtAreaReisespeichern.setText(""); // TextArea zurücksetzen
            for (int i = 0; i < reiseListe.size(); i++) {
                txtAreaReisespeichern.append((i + 1) + ". " + reiseListe.get(i) + "\n\n"); // Nummerierung und Leerzeile
            }

        } catch (Exception ex) {
            // Allgemeine Fehlermeldung als Fallback
            JOptionPane.showMessageDialog(this,
                    "Ein unerwarteter Fehler ist aufgetreten.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}


