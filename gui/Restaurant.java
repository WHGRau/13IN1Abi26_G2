package gui;

/**
 * Beschreiben Sie hier die Klasse Restaurant.
 * 
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Restaurant
{
    private int id;
    private String name;

    public Restaurant() {}

    public Restaurant(String pName) {
        this(-1, pName);
    }

    public Restaurant(int pId, String pName) {
        this.id = pId;
        this.name = pName;
    }

    public void setId(int pId) {
        id = pId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        this.name = pName;
    }
}
