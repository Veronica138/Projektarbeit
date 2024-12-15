package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reiseplanung extends JFrame {
    private javax.swing.JPanel JPanel;
    private javax.swing.JLabel JLabel;
    private JComboBox cBoxUrlaubsort;
    private JLabel JLabelDauer;
    private JLabel JLabelWohnart;
    private JTextField txtFieldDauer;
    private JComboBox cBoxWohnart;
    private JLabel JLabelallinclusive;
    private JComboBox cBoxAllinclusive;
    private JLabel JLabelAnzahlPerson;
    private JTextField txtFieldAnzahl;
    private JButton preisBerechnungButton;
    private JTextField txtFieldAnzahlPersonen;
    private JLabel JLabelVerpflegung;
    private JLabel JLabelSonderwünsche;
    private JCheckBox meerblickCheckBox;
    private JCheckBox terasseCheckBox;
    private JCheckBox poolCheckBox;
    private JCheckBox SPABereichCheckBox;



    public Reiseplanung(){

    setTitle("Reiseplanung");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200,800);
    setContentPane(JPanel);
    setVisible(true);



        txtFieldDauer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {dauer();}
        });
        preisBerechnungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                berechnen();
            }
        });
    }

    public void dauer() {

        try {

            String dauer = txtFieldDauer.getText().toString();

            int Dauer = Integer.parseInt(dauer);


        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Bitte geben sie nur Zahlen ein", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }


         txtFieldAnzahlPersonen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { anzahl();}


         });
         };

             public void anzahl(){
                    try {
                        String anzahl = txtFieldAnzahlPersonen.getText().toString();


                        // Anzahl der Personen sollen abgerufen werden
                        int AnzahlPersonen = Integer.parseInt(anzahl);
                    } catch (Exception e) {


                        JOptionPane.showMessageDialog(this,"Bitte geben sie nur Zahlen ein","Ungültige Eingabe",JOptionPane.WARNING_MESSAGE);
                        throw new RuntimeException(e);
                        }
                    }

public void berechnen(){

                 //hier dann Berechnung von Preis programmieren

}
}







