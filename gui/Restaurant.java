package gui;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Beschreiben Sie hier die Klasse Restaurant.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Restaurant
{
    private String name;

    public Restaurant()
    {
        name = "Unbenanntes Restaurant";
    }
    
    public Restaurant(String name) {
        this.name = name;
    }
    
    public void reserviere(int gastId, int personenzahl, int tischId) {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");

        db.executeStatement(String.format(
            """
            INSERT INTO reservierung (tischId, personenzahl, gastId, zeitpunkt)
            VALUES (%d, %d, %d, '%s');
            """, tischId, personenzahl, gastId, LocalDateTime.now().toString()
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
    
    public Tisch getTischMitMindestkapazitaet(int minKapazitaet)  {
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        db.executeStatement(String.format(
            """
            SELECT * FROM TISCH
            WHERE kapazitaet >= %d
            """, minKapazitaet
        ));
        
        if (db.getErrorMessage() != null) {
            System.out.println(db.getErrorMessage());
        }
        
        java.util.List<Tisch> tische = queryResultToTische(db.getCurrentQueryResult());
        
        return tische.get(0);
    }
    
    public String getName() {
        return name;
    }
    
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
    
    private static String getFromRow(String[] row, int idx) {
        if (row == null) return null;
        return row[idx];
    }
}
