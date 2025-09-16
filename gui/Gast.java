package gui;


/**
 * Beschreiben Sie hier die Klasse Gast.
 * 
 * @author Aghid, Jona , Yannik
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Gast
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String telefonnummer;
    private Benutzerkonto konto;

    /**
     * Konstruktor für Objekte der Klasse Gast
     */
    public Gast(Benutzerkonto pKonto )
    {
        // Instanzvariable initialisieren
        konto = pKonto;

    }


    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public Benutzerkonto getKonto() {
        return konto;
    }

    public void setKonto(Benutzerkonto konto) {
        this.konto = konto;
    }
}

