package gui;
import java.util.ArrayList;
import java.util.List;

/**
 * Beschreiben Sie hier die Klasse Restaurant.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Restaurant
{
    private String name;
    private List<String> reservierteSlots;
    public Restaurant()
    {
        name = "Unbenanntes Restaurant";
    }
    
    public Restaurant(String name) {
        this.name = name;
         this.reservierteSlots = new ArrayList<>();
    }
    
    public void reserviere(Reservierung reservierung) {
        //TODO
    }
    
    public String getName() {
        return name;
    }
    /**
     * Reserviert einen Zeit-Slot zwischen 17:00 und 21:30 in 30 Minuten Schritten
     * @param gastName Name des Gastes
     * @param slot   Zeit-Slot (z.B. "17:00", "17:30", ..., "21:30")
     * @return true wenn erfolgreich ,false wenn ungültig oder belegt 
     */
    
   public boolean reserviereZeitSlot(String gastName, String slot) {
        List<String> erlaubteSlots = List.of(
            "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30",
            "21:00", "21:30"
        );

        if (!erlaubteSlots.contains(slot)) {
            return false; // Slot nicht erlaubt
        }

        if (reservierteSlots.contains(slot)) {
            return false; // Slot schon reserviert
        }
        reservierteSlots.add(slot);
        System.out.println("Reservierung für " + gastName + " um " + slot + " erfolgreich!");
        return true;
    }
    // Alle freien Slots abrufen
        public List<String> getFreieSlots() {
        List<String> erlaubteSlots = List.of(
            "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30",
            "21:00", "21:30"
        );
        List<String> frei = new ArrayList<>(erlaubteSlots);
        frei.removeAll(reservierteSlots);
        return frei;
    }

}
