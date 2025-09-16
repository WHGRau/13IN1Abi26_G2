package gui;


/**
 * Beschreiben Sie hier die Klasse Tisch.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Tisch
{
    private int id;
    private int nummer;
    private int kapazitaet;

    public Tisch (int pNummer, int pKapazitaet) {
        this(-1, pNummer, pKapazitaet);
    }
    
    public Tisch(int pId, int pNummer, int pKapazitaet)
    {
        this.id = pId;
        this.nummer = pNummer;
        this.kapazitaet = pKapazitaet;
    }
    
    public void setId(int pId) {
        id = pId;
    }
    
    public int getId() {
        return id;
    }
    
    public int getNummer() {
        return nummer;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }
}
