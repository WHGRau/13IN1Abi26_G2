package gui;

public class ToDo
{
    // instance variables - replace the example below with your own
    private String  beschreibung;
    private int     prioritaet;

    /**
     * Constructor for objects of class ToDo
     */
    public ToDo(String pBeschreibung, int pPrioritaet)
    {
        beschreibung = pBeschreibung;
        prioritaet   = pPrioritaet;
    }
    
    public String getBeschreibung(){
        return beschreibung;
    }
    
    public int getPrioritaet(){
        return prioritaet;
    }


}
