package gui;
import java.util.ArrayList;
import java.util.List;


/**
 * Beschreiben Sie hier die Klasse LoginHandler.
 * 
 * @author Aghid, Jona , Yannik
 * @version (eine Versionsnummer oder ein Datum)
 */
public class LoginHandler
{
    private static Benutzerkonto angemeldetesKonto;

    /**
     * Konstruktor für Objekte der Klasse LoginHandler
     */
    public LoginHandler()
    {
        // Instanzvariable initialisieren
       
    }

    public Benutzerkonto anmelden(String nameOderEmail, String passwort){
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        System.out.println(db.getErrorMessage());
        db.executeStatement(String.format(
            """
            SELECT * 
            FROM benutzer 
            WHERE (nutzername = '%s' OR email = '%s') 
                AND passwort = '%s';
            """,
            nameOderEmail, nameOderEmail, passwort)
        );
        QueryResult ergebnis = db.getCurrentQueryResult();
        System.out.println(ergebnis);
        if (ergebnis == null || ergebnis.getRowCount() == 0) {
            System.out.println(db.getErrorMessage());
            return null;
        }
        angemeldetesKonto = queryResultToBenutzerkonten(ergebnis).get(0);
        return angemeldetesKonto;
    }
    
    public Benutzerkonto registrieren(String nutzername, String email, String passwort){
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        System.out.println(db.getErrorMessage());
        db.executeStatement(String.format(
            """
            INSERT INTO benutzer 
                (nutzername, passwort, email, vorname, nachname, geburtsdatum)
            VALUES
                ('%s', '%s', '%s', NULL, NULL, NULL)
            """,
            nutzername, passwort, email)
        );
        System.out.println(db.getErrorMessage());
        return anmelden(nutzername, passwort);
    }
    
    public static List<Benutzerkonto> queryResultToBenutzerkonten(QueryResult qr) {
        java.util.List<Benutzerkonto> result = new ArrayList<Benutzerkonto>();
        if (qr == null) return result;

        String[] columnNames = qr.getColumnNames();
        String[][] data = qr.getData();
        if (columnNames == null || data == null) return result;

        int idxNutzername = -1;
        int idxEmail = -1;
        int idxPasswort = -1;
        int idxVorname = -1;
        int idxNachname = -1;
        int idxGeburtsdatum = -1;
        int idxId = -1;
        
        // Bestimmung der indizes
        for (int i = 0; i < columnNames.length; i++) {
            String col = columnNames[i];
            if (col == null) continue;
            switch (col.toLowerCase()) {
                case "nutzername": idxNutzername = i; break;
                case "email": idxEmail = i; break;
                case "passwort": idxPasswort = i; break;
                case "vorname": idxVorname = i; break;
                case "nachname": idxNachname = i; break;
                case "geburtsdatum": idxGeburtsdatum = i; break;
                case "id": idxId = i; break;
                default: break;
            }
        }

        for (String[] row : data) {
            if (row == null) continue;
            Benutzerkonto b = new Benutzerkonto();

            b.setNutzername(getFromRow(row, idxNutzername));
            b.setEmail(getFromRow(row, idxEmail));
            b.setPasswort(getFromRow(row, idxPasswort));
            b.setVorname(getFromRow(row, idxVorname));
            b.setName(getFromRow(row, idxNachname));
            b.setGeburtsdatum(getFromRow(row, idxGeburtsdatum));
            b.setId(Integer.parseInt(getFromRow(row, idxId)));

            result.add(b);
        }

        return result;
    }
    
    private static String getFromRow(String[] row, int idx) {
        if (row == null) return null;
        return row[idx];
    }
    
    public static Benutzerkonto angemeldetAls() {
        return angemeldetesKonto;
    }
 
}
