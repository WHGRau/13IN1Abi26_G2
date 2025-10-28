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

    public static Benutzerkonto anmelden(String nameOderEmail, String passwort){
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        System.out.println(db.getErrorMessage());
        
        db.executeStatement(String.format(
            """
            SELECT id, passwort, salt
            FROM benutzer
            WHERE (nutzername = '%s' OR email = '%s') 
            """, nameOderEmail, nameOderEmail
            )
        );
        QueryResult ergebnis = db.getCurrentQueryResult();
        if (ergebnis == null || ergebnis.getRowCount() == 0) {
            System.out.println("Anmeldedaten falsch");
            return null;
        }
        String id = ergebnis.getData()[0][0];
        String passwortHashDB = ergebnis.getData()[0][1];
        String salt = ergebnis.getData()[0][2];
        
        HashGenerator hasher = new HashGenerator();
        hasher.erzeugeSHA256Hash(passwort, salt);
        
        //Überprüfung, ob der gespeicherte Hash mit dem des eingegebenen Passworts übereinstimmt
        if (!hasher.getErgebnis().equals(passwortHashDB)) {
            System.out.println("Passwort falsch!");
            return null;
        }
        
        db.executeStatement(String.format(
            """
            SELECT * 
            FROM benutzer 
            WHERE id = '%s'
            """,
            id)
        );
        ergebnis = db.getCurrentQueryResult();
        System.out.println(ergebnis);
        if (ergebnis == null || ergebnis.getRowCount() == 0) {
            System.out.println(db.getErrorMessage());
            return null;
        }
        angemeldetesKonto = queryResultToBenutzerkonten(ergebnis).get(0);
        return angemeldetesKonto;
    }
    
    public static Benutzerkonto registrieren(String nutzername, String email, String passwort, String vorname, String nachname){
        DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
        
        HashGenerator hasher = new HashGenerator();
        hasher.erzeugeSHA256Hash(passwort);
        String passwortHash = hasher.getErgebnis();
        String salt         = hasher.getSalt();
        
        System.out.println(db.getErrorMessage());
        db.executeStatement(String.format(
            """
            INSERT INTO benutzer 
                (nutzername, passwort, email, vorname, nachname, geburtsdatum, salt)
            VALUES
                ('%s', '%s', '%s', '%s', '%s', NULL, '%s')
            """,
            nutzername, passwortHash, email, vorname, nachname, salt)
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
    
    public static boolean istAngemeldet() {
        return angemeldetesKonto != null;
    }
 
}
