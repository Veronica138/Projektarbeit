package org.example;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class ReiseplanungTest {
    @Test
    public void testBerechneUndZeigePreis_UngültigDauer() {
        // Vorbereiten von Testdaten
        JTextField txtFieldDauer = new JTextField("abc");
        JTextField txtFieldAnzahlPersonen = new JTextField("3");
        JLabel errorLabelDauer = new JLabel();
        JLabel errorLabelPersonen = new JLabel();

        Reiseplanung reiseplanung = new Reiseplanung();

        //Methode aufrufen
        reiseplanung.berechneUndZeigePreis();
        // Überprüfe, dass das Fehlerlabel für Dauer sichtbar ist
        assertTrue(errorLabelDauer.isVisible());
        assertTrue(errorLabelPersonen.isVisible());


    }
}

