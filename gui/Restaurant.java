package gui;


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
    
    public void reserviere(Reservierung reservierung) {
        //TODO
    }
    
    public String getName() {
        return name;
    }
    
}
