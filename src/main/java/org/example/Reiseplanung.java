package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Reiseplanung extends JFrame {
    // Hauptkomponenten des UI
    private javax.swing.JPanel JPanel;
    private javax.swing.JLabel JLabel;

    // Labels (für die Beschriftung von Eingabefeldern und Bereichen)
    private JLabel JLabelDauer;         // Label für die Eingabe der Reisedauer.
    private JLabel JLabelWohnart;       // Label für die Auswahl der Wohnart (Standard, Deluxe, etc.).
    private JLabel JLabelVerpflegung;   // Label für die Auswahl der Verpflegungsart (Frühstück, All Inclusive, etc.).
    private JLabel JLabelAnzahlPerson;  // Label für die Eingabe der Anzahl der Reisenden.
    private JLabel JLabelSonderwünsche; // Label für die Sonderwünsche-Checkboxen.
    private JLabel JLabelFirmenname;    // Label für den Firmennamen.
    private JLabel JLabelHotel;         // Label für die Hotelauswahl.

    // Eingabekomponenten (für die Benutzereingaben)
    private JComboBox cBoxUrlaubsort;   // Dropdown für die Auswahl des Urlaubsorts.
    private JComboBox cBoxWohnart;      // Dropdown für die Auswahl der Wohnart.
    private JComboBox cBoxAllinclusive; // Dropdown für die Auswahl der Verpflegung.
    private JComboBox comboBox1;        // Dropdown für die Hotelauswahl.
    private JTextField txtFieldDauer;   // Textfeld für die Eingabe der Reisedauer in Tagen.
    private JTextField txtFieldAnzahlPersonen; // Textfeld für die Eingabe der Anzahl der Reisenden.

    // Checkboxes (für Sonderwünsche)
    private JCheckBox SPABereich;       // Checkbox für die Option "SPA-Bereich".
    private JCheckBox pool;             // Checkbox für die Option "Pool".
    private JCheckBox meerblick;        // Checkbox für die Option "Meerblick".
    private JCheckBox terasse;          // Checkbox für die Option "Terrasse".

    // Buttons (für verschiedene Aktionen)
    private JButton preisBerechnungButton; // Button zum Berechnen des Reisepreises.
    private JButton buttonSpeichern;       // Button zum Speichern der Reise in die Liste.
    private JButton buttonListeLoeschen;   // Button zum Löschen der gespeicherten Liste.
    private JButton buttonReset;           // Button zum Zurücksetzen aller Eingabefelder.

    // TextArea (für die Anzeige gespeicherter Reisen)
    private JTextArea txtAreaReisespeichern; // Bereich, in dem die Liste der gespeicherten Reisen angezeigt wird.

    // Fehlerlabels (für Validierungs- und Eingabefehler)
    private JLabel errorLabelPersonen;      // Fehlerlabel für ungültige Eingaben der Personenanzahl.
    private JLabel errorLabelDauer;         // Fehlerlabel für ungültige Eingaben der Reisedauer.
    private JLabel errorLabelHotel;         // Fehlerlabel für ungültige oder fehlende Hotelauswahl.
    private JLabel errorLabelUrlaubsort;    // Fehlerlabel für ungültige oder fehlende Urlaubsort-Auswahl.
    private JLabel errorLabelWohnart;       // Fehlerlabel für ungültige oder fehlende Wohnart-Auswahl.
    private JLabel errorLabelVerpflegung;   // Fehlerlabel für ungültige oder fehlende Verpflegungsauswahl.


    // ArrayList zum Speichern der Reisen
    private ArrayList<String> reiseListe = new ArrayList<>();


    public Reiseplanung() {

        setTitle("Reiseplanung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1000);
        setContentPane(JPanel);
        setVisible(true);
        System.out.print(comboBox1.getItemCount());
        setResizable(false);


        //Combobox für Hotel erstellt und soll von der Arrayliste die Hotelnamen anzeigen
        ArrayList<Hotel> hotels = Hotel.getHotels();
        for (Hotel hotel : hotels) {
            this.comboBox1.addItem(hotel.Hotelnamen);
        }


        this.comboBox1.revalidate();
        this.comboBox1.repaint();


        // Fehlerlabels standardmäßig unsichtbar setzen
        errorLabelDauer.setVisible(false);
        errorLabelPersonen.setVisible(false);
        errorLabelHotel.setVisible(false);
        errorLabelWohnart.setVisible(false);
        errorLabelVerpflegung.setVisible(false);
        errorLabelUrlaubsort.setVisible(false);

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

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFieldDauer.setText("");
                txtFieldAnzahlPersonen.setText("");

                if (cBoxUrlaubsort.getItemCount() > 0) {
                    cBoxUrlaubsort.setSelectedIndex(0);
                }
                if (cBoxWohnart.getItemCount() > 0) {
                    cBoxWohnart.setSelectedIndex(0);
                }
                if (cBoxAllinclusive.getItemCount() > 0) {
                    cBoxAllinclusive.setSelectedIndex(0);
                }
                if (comboBox1.getItemCount() > 0) {
                    comboBox1.setSelectedIndex(0);
                }

                    SPABereich.setSelected(false);
                pool.setSelected(false);
                meerblick.setSelected(false);
                terasse.setSelected(false);

                errorLabelDauer.setVisible(false);
                errorLabelPersonen.setVisible(false);
            }
        });
    }



    // Methode zur Preisberechnung und Anzeige
    public void berechneUndZeigePreis() {
        // Variablen zur Fehlererfassung
        boolean fehlerInDauer = false;
        boolean fehlerInPersonen = false;
        boolean fehlerInUrlaubsort = false;
        boolean fehlerInWohnart = false;
        boolean fehlerInVerpflegung = false;
        boolean fehlerInHotel = false;

        // Dauer überprüfen
        String dauerText = txtFieldDauer.getText();
        if (!dauerText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
            fehlerInDauer = true;
            errorLabelDauer.setVisible(true); // Fehlerlabel für Dauer anzeigen
        } else {
            errorLabelDauer.setVisible(false); // Fehlerlabel bei korrekter Eingabe unsichtbar machen
        }

        // Personenzahl überprüfen
        String personenText = txtFieldAnzahlPersonen.getText();
        if (!personenText.matches("\\d+")) { // Nur ganze Zahlen erlaubt
            fehlerInPersonen = true;
            errorLabelPersonen.setVisible(true); // Fehlerlabel für Personenanzahl anzeigen
        } else {
            errorLabelPersonen.setVisible(false); // Fehlerlabel bei korrekter Eingabe unsichtbar machen
        }

        // Urlaubsort überprüfen
        String urlaubsort = (String) cBoxUrlaubsort.getSelectedItem();
        if (urlaubsort == null || urlaubsort.equals("--Auswählen--")) {
            fehlerInUrlaubsort = true;
            errorLabelUrlaubsort.setVisible(true); // Fehlerlabel für Urlaubsort anzeigen
        } else {
            errorLabelUrlaubsort.setVisible(false);
        }

        // Wohnart überprüfen
        String wohnart = (String) cBoxWohnart.getSelectedItem();
        if (wohnart == null || wohnart.equals("--Auswählen--")) {
            fehlerInWohnart = true;
            errorLabelWohnart.setVisible(true); // Fehlerlabel für Wohnart anzeigen
        } else {
            errorLabelWohnart.setVisible(false);
        }

        // Verpflegung überprüfen
        String allInclusive = (String) cBoxAllinclusive.getSelectedItem();
        if (allInclusive == null || allInclusive.equals("--Auswählen--")) {
            fehlerInVerpflegung = true;
            errorLabelVerpflegung.setVisible(true); // Fehlerlabel für Verpflegung anzeigen
        } else {
            errorLabelVerpflegung.setVisible(false);
        }

        // Hotel überprüfen
        String hotel = (String) comboBox1.getSelectedItem();
        if (hotel == null || hotel.equals("--Auswählen--")) {
            fehlerInHotel = true;
            errorLabelHotel.setVisible(true); // Fehlerlabel für Hotel anzeigen
        } else {
            errorLabelHotel.setVisible(false);
        }

        // Wenn Fehler aufgetreten sind, entsprechende Nachricht anzeigen
        if (fehlerInDauer || fehlerInPersonen || fehlerInUrlaubsort || fehlerInWohnart || fehlerInVerpflegung || fehlerInHotel) {
            StringBuilder fehlermeldung = new StringBuilder("Bitte beheben Sie die folgenden Fehler:\n");
            if (fehlerInUrlaubsort) fehlermeldung.append("- Bitte wählen Sie einen Urlaubsort aus.\n");
            if (fehlerInHotel) fehlermeldung.append("- Bitte wählen Sie ein Hotel aus.\n");
            if (fehlerInDauer) fehlermeldung.append("- Ungültige Dauer (nur ganze Zahlen erlaubt).\n");
            if (fehlerInWohnart) fehlermeldung.append("- Bitte wählen Sie eine Wohnart aus.\n");
            if (fehlerInVerpflegung) fehlermeldung.append("- Bitte wählen Sie eine Verpflegungsoption aus.\n");
            if (fehlerInPersonen) fehlermeldung.append("- Ungültige Personenanzahl (nur ganze Zahlen erlaubt).\n");


            JOptionPane.showMessageDialog(this, fehlermeldung.toString(), "Ungültige Eingaben", JOptionPane.WARNING_MESSAGE);
            return; // Methode verlassen
        }

        // Wenn keine Fehler aufgetreten sind, Informationen verarbeiten
        try {
            int dauer = Integer.parseInt(txtFieldDauer.getText());
            int personen = Integer.parseInt(txtFieldAnzahlPersonen.getText());

            // Berechnung des Preises
            double preis = berechnePreis(dauer, personen, wohnart, allInclusive);

            // Zeige den berechneten Preis in einem Dialogfeld
            JOptionPane.showMessageDialog(this, "Ihre ausgewählte Reise kostet " + String.format("%.2f €", preis),
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
                grundpreisProTag = 50.0;
                break;
            case "Barcelona":
                grundpreisProTag = 50.0;
                break;
            case "Berlin":
                grundpreisProTag = 30.0;
                break;
            case "Dubai":
                grundpreisProTag = 60.0;
                break;
            case "London":
                grundpreisProTag = 40.0;
                break;
            case "Madrid":
                grundpreisProTag = 45.0;
                break;
            case "Mailand":
                grundpreisProTag = 30.0;
                break;
            case "München":
                grundpreisProTag = 50.0;
                break;
            case "New York":
                grundpreisProTag = 60.0;
                break;
            case "Paris":
                grundpreisProTag = 55.0;
                break;
            case "Rom":
                grundpreisProTag = 40.0;
                break;

        }
        String Hotel = (String) comboBox1.getSelectedItem();
        switch(Hotel){
            case "Mercy Hotel":
                grundpreisProTag += 55.99;
                break;
            case "Olive Hotel":
                grundpreisProTag += 20.99;
                break;
            case "Modern Hotel":
                grundpreisProTag += 60.59;
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
        try {
            // Dauer und Personenanzahl prüfen
            String dauerText = txtFieldDauer.getText();
            int dauer = Integer.parseInt(dauerText);
            String personenText = txtFieldAnzahlPersonen.getText();
            int personen = Integer.parseInt(personenText);

            // Alle benötigten Felder abrufen
            String urlaubsort = (String) cBoxUrlaubsort.getSelectedItem();
            String wohnart = (String) cBoxWohnart.getSelectedItem();
            String allInclusive = (String) cBoxAllinclusive.getSelectedItem();
            String hotel = (String) comboBox1.getSelectedItem();
            String Hotel = (String) comboBox1.getSelectedItem();

            //Sonderwünsche sammeln
            ArrayList<String>sonderwuensche = new ArrayList<>();
            if (SPABereich.isSelected())sonderwuensche.add ("SPABereich");
            if (pool.isSelected())sonderwuensche.add("Pool");
            if ( meerblick.isSelected())sonderwuensche.add("Meerblick");
            if ( terasse.isSelected())sonderwuensche.add(" Terasse");

            // Überprüft ob die Liste der Sonderwünsche leer ist
            // Wenn die Liste leer ist, wird "Keine Sonderwünsche" als Standardwert zugewiesen
            // Sonst werden die Sonderwünsche in die Liste hinzugefügt
            // Überprüfen, ob die Liste der Sonderwünsche leer ist
            String sonderwuenscheText = sonderwuensche.isEmpty()
                    ? "Keine Sonderwünsche ausgewählt"
                    : String.join(", ", sonderwuensche);


            // Preis berechnen
            double preis = berechnePreis(dauer, personen, wohnart, allInclusive);

            // Reiseinformationen zusammenstellen
            String reise = String.format(
                    "Urlaubsziel: %s\n" +
                            "Wohnart: %s\n" +
                            "Hotel: %s\n" +
                            "Verpflegung: %s\n" +
                            "Sonderwünsche: %s\n"+
                            "Dauer: %d Tage\n" +
                            "Personen: %d\n" +
                            "Preis: %.2f €",
                    urlaubsort, wohnart, hotel, allInclusive, sonderwuenscheText , dauer, personen, preis);

            // Reiseinformationen in die Liste speichern
            reiseListe.add(reise);

            // TextArea aktualisieren
            txtAreaReisespeichern.setText(""); // TextArea zurücksetzen
            for (int i = 0; i < reiseListe.size(); i++) {
                txtAreaReisespeichern.append((i + 1) + ". " + reiseListe.get(i) + "\n\n"); // Nummerierung und Leerzeile
            }

            // Erfolgsmeldung
            JOptionPane.showMessageDialog(this, "Reise erfolgreich gespeichert!",
                    "Erfolg", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ein unerwarteter Fehler ist aufgetreten: " + ex.getMessage(),
                    "Fehler", JOptionPane.ERROR_MESSAGE);

        }
    }
}