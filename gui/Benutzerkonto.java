package gui;


/**
 * Beschreiben Sie hier die Klasse Benutzer.
 * 
 * @author Aghid, Jona, Yannik
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Benutzerkonto
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String nutzername;
    private String email;
    private String passwort;
    private String geburtsdatum;
    private String vorname;
    private String name;
    private int id;

    /**
     * Konstruktor für Objekte der Klasse Benutzer
     */
    public Benutzerkonto(String pNutzername, String pEmail, String pPasswort, String pGeburtsdatum, String pVorname, String pName )
    {
        // Instanzvariable initialisieren
        nutzername = pNutzername;
        email = pEmail;
        passwort = pPasswort;
        geburtsdatum = pGeburtsdatum;
        vorname = pVorname;
        name = pName;
    }
    
    public Benutzerkonto() {}

     public String getNutzername() {
        return nutzername;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPasswort() {
        return passwort;
    }
    
    public String getGeburtsdatum() {
        return geburtsdatum;
    }
    
    public String getVorname() {
        return vorname;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }

    public void setNutzername(String nutzername) { this.nutzername = nutzername; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswort(String passwort) { this.passwort = passwort; }
    public void setGeburtsdatum(String geburtsdatum) { this.geburtsdatum = geburtsdatum; }
    public void setVorname(String vorname) { this.vorname = vorname; }
    public void setName(String name) { this.name = name; }
    public void setId(int id) {this.id = id;}

}