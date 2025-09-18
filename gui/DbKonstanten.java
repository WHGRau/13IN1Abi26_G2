package gui;


/**
 * Diese Klasse enthält alle Konstanten, die in Verbindung
 * zu den Tabellen der Datenbank stehen. Für einfache zukünftige
 * Änderungen sollten Tabellennamen o.Ä. ausschließlich über
 * die Konstanten in dieser Klasse abgerufen werden.
 */
public final class DbKonstanten
{
    //Klasse nicht instanzierbar
    private DbKonstanten(){}
    
    public static final String DB_RESTAURANT = "restaurant_db";
    
    public static final String TABELLE_BENUTZER = "benutzer";
    public static final String TABELLE_GAST = "gast";
    public static final String TABELLE_MITARBEITER = "mitarbeiter";
    public static final String TABELLE_TISCH = "tisch";
    public static final String TABELLE_RESERVIERUNG = "reservierung";

}
