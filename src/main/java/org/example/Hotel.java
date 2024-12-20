package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class Hotel {
    public String Hotelnamen;
    public double HotelPreis;
    public boolean AllInclusive;

    public Hotel (String Hotelnamen, double HotelPreis, boolean AllIclusive){
        this.Hotelnamen = Hotelnamen;
        this.HotelPreis = HotelPreis;
        this.AllInclusive=AllIclusive;
    }

    public static ArrayList<Hotel> getHotels(){
        ArrayList<Hotel> hotels = new ArrayList<>();
//3 Objekte erstellt mit den Attributen Hotelnamen, Hotelpreis und AllInclusive(Später noch in der Klasse Reiseplanung.java Datei den Hotelpreis in den GesamtPreis hinzufügen)
        hotels.add(new Hotel("Mercy Hotel", 55.99, true));
        hotels.add(new Hotel("Olive Hotel", 20.99, false));
        hotels.add(new Hotel("Modern Hotel", 60.59, true));
        return hotels;
    }



}

