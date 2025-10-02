package gui;
import java.time.LocalDateTime;


/**
 * Beschreiben Sie hier die Klasse Reservierung.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Reservierung
{
    private int id;
    private LocalDateTime zeitpunkt;
    private int tisch;
    private int personenzahl;
    private int gastId;
    
    public Reservierung() {}

    public Reservierung(LocalDateTime pZeit, int pInt, int pPersonenzahl, int pGast) {
        this(-1, pZeit, pInt, pPersonenzahl, pGast);        
    }
    
    public Reservierung(int pId, LocalDateTime pZeit, int pInt, int pPersonenzahl, int pGast)
    {
        this.id = pId;
        this.zeitpunkt = pZeit;
        this.personenzahl = pPersonenzahl;
        this.gastId = pGast;
    }
    
    public void setId(int pId) {
        id = pId;
    }
    
    public int getId() {
        return id;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public int getInt() {
        return tisch;
    }

    public int getPersonenzahl() {
        return personenzahl;
    }

    public int getGastId() {
        return gastId;
    }
    
    public void setZeitpunkt(LocalDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }
    
    public void setTisch(int tisch) {
        this.tisch = tisch;
    }
    
    public void setPersonenzahl(int personenzahl) {
        this.personenzahl = personenzahl;
    }
    
    public void setGast(int gast) {
        this.gastId = gast;
    }
    
    public int getTisch() {
        return tisch;
    }
    

}
