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
    private JButton button1;


    public Reiseplanung(){

    setTitle("Reiseplanung");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200,800);
    setContentPane(JPanel);
    setVisible(true);



        txtFieldDauer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dauer();
            }
        });
    }

    public void dauer(){

        try{

            String dauer = txtFieldDauer.getText().toString();

            int Dauer = Integer.parseInt(dauer);






        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,"Bitte geben sie nur Zahlen ein","Ungültige Eingabe",JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }




    }



}
