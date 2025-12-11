package gui;

import java.time.LocalDateTime;

/**
 * Beschreiben Sie hier die Klasse Bewertung.
 *
 * Felder: id, kommentar, punkte, zeitpunkt, benutzerId, restaurantId
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Bewertung
{
    private int id;
    private String kommentar;    // optional
    private int punkte;         // numerischer Wert, z.B. 1..5
    private LocalDateTime zeitpunkt;
    private int benutzerId;     // Fremdschlüssel auf Benutzer (Gast)
    private int restaurantId;   // Fremdschlüssel auf Restaurant

    public Bewertung() {}

    public Bewertung(int pPunkte, int pBenutzerId, int pRestaurantId) {
        this(-1, null, pPunkte, LocalDateTime.now(), pBenutzerId, pRestaurantId);
    }

    public Bewertung(int pId, String pKommentar, int pPunkte, LocalDateTime pZeit, int pBenutzerId, int pRestaurantId) {
        this.id = pId;
        this.kommentar = pKommentar;
        this.punkte = pPunkte;
        this.zeitpunkt = pZeit;
        this.benutzerId = pBenutzerId;
        this.restaurantId = pRestaurantId;
    }

    public void setId(int pId) {
        id = pId;
    }

    public int getId() {
        return id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String pKommentar) {
        kommentar = pKommentar;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int pPunkte) {
        punkte = pPunkte;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public void setZeitpunkt(LocalDateTime pZeit) {
        zeitpunkt = pZeit;
    }

    public int getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(int pBenutzerId) {
        benutzerId = pBenutzerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int pRestaurantId) {
        restaurantId = pRestaurantId;
    }
}
