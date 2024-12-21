package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Reiseplanung extends JFrame {
    private javax.swing.JPanel JPanel;
    private javax.swing.JLabel JLabel;
    private JComboBox cBoxUrlaubsort;
    private JLabel JLabelDauer;
    private JLabel JLabelWohnart;
    private JTextField txtFieldDauer;
    private JComboBox cBoxWohnart;
    private JLabel JLabelVerpflegung;
    private JComboBox cBoxAllinclusive;
    private JLabel JLabelAnzahlPerson;
    private JLabel JLabelSonderwünsche;
    private JCheckBox SPABereich;
    private JCheckBox pool;
    private JCheckBox meerblick;
    private JCheckBox terasse;
    private JTextField txtFieldAnzahlPersonen;
    private JButton preisBerechnungButton;
    private JButton buttonSpeichern;
    private JTextArea txtAreaReisespeichern; // Neue TextArea für die Liste
    private JButton buttonListeLoeschen; // Button zum Löschen der Liste
    private JLabel errorLabelPersonen;
    private JLabel errorLabelDauer;
    private JButton buttonReset;
    private JComboBox comboBox1;

    // ArrayList zum Speichern der Reisen
    private ArrayList<String> reiseListe = new ArrayList<>();


    public Reiseplanung() {

        setTitle("Reiseplanung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setContentPane(JPanel);
        setVisible(true);
        System.out.print(comboBox1.getItemCount());


        //Combobox für Hotel erstellt und soll von der Arrayliste die Hotelnamen anzeigen
        ArrayList<Hotel> hotels = Hotel.getHotels();
        for (Hotel hotel : hotels){
            this.comboBox1.addItem(hotel.Hotelnamen);
        }


        this.comboBox1.revalidate();
        this.comboBox1.repaint();


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
    }



    // Methode zur Preisberechnung und Anzeige
    private void berechneUndZeigePreis() {
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
        double grundpreisProTag = 0.0; // Startwert für den Preis

        // Preise basierend auf dem ausgewählten Ort
        String urlaubsort = (String) cBoxUrlaubsort.getSelectedItem();
        switch (urlaubsort) {
            case "Amsterdam":
                grundpreisProTag = 130.0;
                break;
            case "Barcelona":
                grundpreisProTag = 130.0;
                break;
            case "Berlin":
                grundpreisProTag = 100.0;
                break;
            case "Dubai":
                grundpreisProTag = 200.0;
                break;
            case "London":
                grundpreisProTag = 175.0;
                break;
            case "Madrid":
                grundpreisProTag = 130.0;
                break;
            case "Mailand":
                grundpreisProTag = 135.0;
                break;
            case "München":
                grundpreisProTag = 115.0;
                break;
            case "New York":
                grundpreisProTag = 225.0;
                break;
            case "Paris":
                grundpreisProTag = 180.0;
                break;
            case "Rom":
                grundpreisProTag = 140.0;
                break;
        }

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

        // Zuschläge für Verpflegung
        switch (allInclusive) {
            case "Frühstück inbegriffen":
                grundpreisProTag += 10;
                break;
            case "Abendessen inbegriffen":
                grundpreisProTag += 20;
                break;
            case "Halbpension":
                grundpreisProTag += 25;
                break;
            case "Vollpension":
                grundpreisProTag += 40;
                break;
            case "All Inclusive":
                grundpreisProTag += 60;
                break;
        }

        // Zuschläge für Sonderwünsche
        if (SPABereich.isSelected()) {
            grundpreisProTag += 10; // Preis für SPA pro Tag
        }
        if (pool.isSelected()) {
            grundpreisProTag += 15; // Preis für Pool pro Tag
        }
        if (meerblick.isSelected()) {
            grundpreisProTag += 20; // Preis für Meerblick pro Tag
        }
        if (terasse.isSelected()) {
            grundpreisProTag += 5; // Preis für Terrasse pro Tag
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
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige ganze Zahl für die Tage ein.",
                        "Ungültige Eingabe für Dauer", JOptionPane.WARNING_MESSAGE);
                return; // Methode verlassen
            }
            int dauer = Integer.parseInt(dauerText);

            // Personenzahl überprüfen
            String personenText = txtFieldAnzahlPersonen.getText();
            if (!personenText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
                errorLabelPersonen.setVisible(true); // Fehlerlabel für Personenanzahl anzeigen
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige ganze Zahl für die Personenzahl ein.",
                        "Ungültige Eingabe für Personenzahl", JOptionPane.WARNING_MESSAGE);
                return; // Methode verlassen
            }
            int personen = Integer.parseInt(personenText);

            // Informationen für die Reise sammeln
            String urlaubsort = (String) cBoxUrlaubsort.getSelectedItem();
            String wohnart = (String) cBoxWohnart.getSelectedItem();
            String allInclusive = (String) cBoxAllinclusive.getSelectedItem();
            String hotel = (String) comboBox1.getSelectedItem();
            double preis = berechnePreis(dauer, personen, wohnart, allInclusive);


            // Reiseinformationen zusammenstellen mit „Ort:“ vor dem Namen
            String reise = String.format(
                    "Urlaubsziel:\nOrt: %s\n" +
                            "Wohnart: %s\n" +
                            "Hotel: %s\n"  +
                            "All-Inclusive: %s\n" +
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
            JOptionPane.showMessageDialog(this, "Ein unerwarteter Fehler ist aufgetreten.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
    }



