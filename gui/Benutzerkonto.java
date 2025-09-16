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


}