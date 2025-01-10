package org.example;

import java.util.ArrayList;

public class Hotel {
    public String Hotelnamen;
    public double HotelPreis;
    public boolean AllInclusive;

    public Hotel(String auswählen){
        initObjekte();
    }
    public Hotel (String Hotelnamen, double HotelPreis, boolean AllIclusive ){
        this.Hotelnamen = Hotelnamen;
        this.HotelPreis = HotelPreis;
        this.AllInclusive=AllIclusive;

    }
    private void initObjekte(){
        this.Hotelnamen = "Standard Hotel";
        this.HotelPreis = 0.0;
        this.AllInclusive = false;
    }

    public static ArrayList<Hotel> getHotels(){
        ArrayList<Hotel> hotels = new ArrayList<>();
//3 Objekte erstellt mit den Attributen Hotelnamen, Hotelpreis und AllInclusive(Später noch in der Klasse Reiseplanung.java Datei den Hotelpreis in den GesamtPreis hinzufügen)
        hotels.add(new Hotel("--Auswählen--", 0.0, false));
        hotels.add(new Hotel("Mercy Hotel", 55.99, true));
        hotels.add(new Hotel("Olive Hotel", 20.99, false));
        hotels.add(new Hotel("Modern Hotel", 60.59, true));
        hotels.add(new Hotel("Sunrise Paradise", 45.50, true));
        hotels.add(new Hotel("Grand Elite", 75.00, true));
        hotels.add(new Hotel("Ocean Breeze", 30.25, false));
        hotels.add(new Hotel("Cityscape Inn", 40.00, true));
        hotels.add(new Hotel("Mountain Retreat", 50.75, false));
        return hotels;
    }



}

