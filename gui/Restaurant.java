package gui;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.*;
import java.time.format.*;

import java.util.List;

/**
 * Beschreiben Sie hier die Klasse Restaurant.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Restaurant
{
    private String name;
    private List<String> reservierteSlots;
    public Restaurant()
    {
        name = "Unbenanntes Restaurant";
    }
    
    public Restaurant(String name) {
        this.name = name;
         this.reservierteSlots = new ArrayList<>();
    }
    
    public boolean reserviere(int gastId, int personenzahl, String zeitpunktString) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime zeitpunkt = LocalDateTime.parse(zeitpunktString, format);
        Tisch verfuegbarerTisch = getVerfuegbarenTisch(personenzahl, zeitpunkt);
        if (verfuegbarerTisch == null) return false;
        reserviere(gastId, personenzahl, verfuegbarerTisch.getId(), zeitpunkt);
        return true;
    }
    
    public void reserviere(int gastId, int personenzahl, int tischId, LocalDateTime zeitpunkt) {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");

        db.executeStatement(String.format(
            """
            INSERT INTO reservierung (tischId, personenzahl, gastId, zeitpunkt)
            VALUES (%d, %d, %d, '%s');
            """, tischId, personenzahl, gastId, zeitpunkt.toString()
        ));
        System.out.println(db.getErrorMessage());
    }
    
    public Reservierung reserviere(Reservierung reservierung) {
        if (LoginHandler.angemeldetAls() == null) {
            System.out.println("Nicht angemeldet");
        }
        int gastId = LoginHandler.angemeldetAls().getId();
        int personenzahl = reservierung.getPersonenzahl();
        int tischId = reservierung.getTisch().getId();
        LocalDateTime zeitpunkt = reservierung.getZeitpunkt();
        String zeitpunktString = zeitpunkt.toString();
        
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");

        db.executeStatement(String.format(
            """
            INSERT INTO reservierung (tischId, personenzahl, gastId, zeitpunkt)
            VALUES (%d, %d, %d, %s);
            """, tischId, personenzahl, gastId, zeitpunktString
        ));
        
        if (db.getErrorMessage() != null) {
            System.out.println(db.getErrorMessage());
        }
        
        return reservierung;
    }
    
    public Tisch getVerfuegbarenTisch(int minKapazitaet, LocalDateTime zeitpunkt) {
        java.util.List<Tisch> tische = getTischeMitMindestkapazitaet(minKapazitaet);
        for (Tisch tisch : tische) {
            if (istTischVerfuegbar(tisch.getId(), zeitpunkt)) {
                return tisch;
            }
        }
        
        return null;
    }
    
    public Tisch getVerfuegbarenTisch(int minKapazitaet, String zeitpunktString) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime zeitpunkt = LocalDateTime.parse(zeitpunktString, format);
        return getVerfuegbarenTisch(minKapazitaet, zeitpunkt);
    }
    
    public java.util.List<Tisch> getTischeMitMindestkapazitaet(int minKapazitaet)  {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        db.executeStatement(String.format(
            """
            SELECT * FROM TISCH
            WHERE kapazitaet >= %d
            ORDER BY kapazitaet
            """, minKapazitaet
        ));
        
        if (db.getErrorMessage() != null) {
            System.out.println(db.getErrorMessage());
        }
        
        java.util.List<Tisch> tische = queryResultToTische(db.getCurrentQueryResult());
        
        return tische;
    }
    
    public boolean istTischVerfuegbar(int tischId, LocalDateTime zeitpunkt) {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        
        String zeitpunktString = zeitpunkt.toString();
        
        db.executeStatement(String.format(
            """
            SELECT * 
            FROM reservierung
            WHERE tischId = 0
            	AND '%s' > zeitpunkt 
                AND '%s' < ADDTIME(zeitpunkt, '4:00:00');
            """, zeitpunktString, zeitpunktString
        ));
        
        if (db.getErrorMessage() != null) {
            System.out.println(db.getErrorMessage());
        }
        
        if (db.getCurrentQueryResult().getRowCount() > 0) {
            System.out.println("Tisch bereits geblockt");
            return false;
        }
        
        System.out.println("Tisch noch frei");
        return true;
    }

    public java.util.List<Reservierung> getReservierungenAnTag(String tag) {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        db.executeStatement(String.format(
            """
            SELECT * FROM reservierung
            WHERE DATE(reservierung.zeitpunkt) = '%s';
            """,
            tag
        ));
        return queryResultToReservierungen(db.getCurrentQueryResult());
    }
    
    public String getName() {
        return name;
    }
    /**
     * Reserviert einen Zeit-Slot zwischen 17:00 und 21:30 in 30 Minuten Schritten
     * @param gastName Name des Gastes
     * @param slot   Zeit-Slot (z.B. "17:00", "17:30", ..., "21:30")
     * @return true wenn erfolgreich ,false wenn ungültig oder belegt 
     */
    
    public static java.util.List<Tisch> queryResultToTische(QueryResult qr) {
        java.util.List<Tisch> result = new ArrayList<>();
        if (qr == null) return result;

        String[] columnNames = qr.getColumnNames();
        String[][] data = qr.getData();
        if (columnNames == null || data == null) return result;

        int idxId = -1;
        int idxNummer = -1;
        int idxKapazitaet = -1;
        
        // Bestimmung der indizes
        for (int i = 0; i < columnNames.length; i++) {
            String col = columnNames[i];
            if (col == null) continue;
            switch (col.toLowerCase()) {
                case "nummer": idxNummer = i; break;
                case "kapazitaet": idxKapazitaet = i; break;
                case "id": idxId = i; break;
                default: break;
            }
        }

        for (String[] row : data) {
            if (row == null) continue;
            Tisch t = new Tisch();

            t.setId(Integer.parseInt(getFromRow(row, idxId)));
            t.setNummer(Integer.parseInt(getFromRow(row, idxNummer)));
            t.setKapazitaet(Integer.parseInt(getFromRow(row, idxKapazitaet)));

            result.add(t);
        }

        return result;
    }
    
    public static java.util.List<Reservierung> queryResultToReservierungen(QueryResult qr) {
        java.util.List<Reservierung> result = new ArrayList<>();
        if (qr == null) return result;

        String[] columnNames = qr.getColumnNames();
        String[][] data = qr.getData();
        if (columnNames == null || data == null) return result;

        int idxId = -1;
        int idxGastId = -1;
        int idxPersonenzahl = -1;
        int idxTischId = -1;
        int idxZeitpunkt = -1;
        
        // Bestimmung der indizes
        for (int i = 0; i < columnNames.length; i++) {
            String col = columnNames[i];
            if (col == null) continue;
            switch (col.toLowerCase()) {
                case "id": idxId = i; break;
                case "gastId": idxGastId = i; break;
                case "personenzahl": idxPersonenzahl = i; break;
                case "tischId": idxTischId = i; break;
                case "zeitpunkt": idxZeitpunkt = i; break;
                default: break;
            }
        }

        for (String[] row : data) {
            if (row == null) continue;
            Reservierung r = new Reservierung();

            r.setId(Integer.parseInt(getFromRow(row, idxId)));
            r.setGast(getFromRowAsInt(row, idxGastId));
            r.setPersonenzahl(getFromRowAsInt(row, idxPersonenzahl));
            r.setTisch(getFromRowAsInt(row, idxTischId));
            
            String zeitpunktString = getFromRow(row, idxZeitpunkt);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime zeitpunkt = LocalDateTime.parse(zeitpunktString, format);
            r.setZeitpunkt(zeitpunkt);
            
            result.add(r);
        }

        return result;
    }
    
    private static String getFromRow(String[] row, int idx) {
        if (row == null || idx < 0) return null;
        return row[idx];
    }
    
    private static int getFromRowAsInt(String[] row, int idx) {
        if (row == null || idx < 0) return -1;
        String wert = row[idx];
        if (wert == null) return -1;
        return Integer.parseInt(wert);
    }

   public boolean reserviereZeitSlot(String gastName, String slot) {
        List<String> erlaubteSlots = List.of(
            "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30",
            "21:00", "21:30"
        );

        if (!erlaubteSlots.contains(slot)) {
            return false; // Slot nicht erlaubt
        }

        if (reservierteSlots.contains(slot)) {
            return false; // Slot schon reserviert
        }
        reservierteSlots.add(slot);
        System.out.println("Reservierung für " + gastName + " um " + slot + " erfolgreich!");
        return true;
    }
    // Alle freien Slots abrufen
        public List<String> getFreieSlots() {
        List<String> erlaubteSlots = List.of(
            "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30",
            "21:00", "21:30"
        );
        List<String> frei = new ArrayList<>(erlaubteSlots);
        frei.removeAll(reservierteSlots);
        return frei;
    }

}
