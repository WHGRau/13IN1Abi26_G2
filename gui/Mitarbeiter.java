package gui;


/**
 * Beschreiben Sie hier die Klasse Mitarbeiter.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Mitarbeiter
{
    private int id;
    private Benutzerkonto konto;

    public Mitarbeiter(int pId, Benutzerkonto pKonto)
    {
        this.id = pId;
        this.konto = pKonto;
    }
    
    public Mitarbeiter(Benutzerkonto pKonto) {
        this.konto = pKonto;
    }
    
    
    public void beginneArbeit() {
        //TODO
    }
    
    public void beendeArbeit() {
        //TODO
    }
    
    public void setId(int pId) {
        id = pId;
    }
    
    public int getId() {
        return id;
    }
    
    public Benutzerkonto getKonto() {
        return konto;
    }

}
