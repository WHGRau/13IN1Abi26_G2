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
    private Tisch tisch;
    private int personenzahl;
    private Gast gast;
    
    public Reservierung() {}

    public Reservierung(LocalDateTime pZeit, Tisch pTisch, int pPersonenzahl, Gast pGast) {
        this(-1, pZeit, pTisch, pPersonenzahl, pGast);        
    }
    
    public Reservierung(int pId, LocalDateTime pZeit, Tisch pTisch, int pPersonenzahl, Gast pGast)
    {
        this.id = pId;
        this.zeitpunkt = pZeit;
        this.personenzahl = pPersonenzahl;
        this.gast = pGast;
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

    public Tisch getTisch() {
        return tisch;
    }

    public int getPersonenzahl() {
        return personenzahl;
    }

    public Gast getGast() {
        return gast;
    }
    
    public void setZeitpunkt(LocalDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }
    
    public void setTisch(Tisch tisch) {
        this.tisch = tisch;
    }
    
    public void setPersonenzahl(int personenzahl) {
        this.personenzahl = personenzahl;
    }
    
    public void setGast(Gast gast) {
        this.gast = gast;
    }
    
    public void setGast(int gastId) {
        
    }
    
    public void setTisch(int tischId) {
        
    }

}
