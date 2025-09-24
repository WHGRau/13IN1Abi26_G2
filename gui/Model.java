package gui;

/**
 * Write a description of class Model here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Model
{
    // instance variables - replace the example below with your own
    private List<ToDo> toDoList;

    /**
     * Constructor for objects of class Model
     */
    public Model()
    {
        toDoList = new List<ToDo>();       
    }
    
    public List<ToDo> getToDoList(){
        return toDoList;
    }
    
    private boolean inList(String pBeschreibung){
        toDoList.toFirst();
        while(toDoList.hasAccess()){
            if(toDoList.getContent().getBeschreibung().equals(pBeschreibung))
                return true;
            toDoList.next();
        }
        return false;
    }
    
    public void addToDo(String pBeschreibung, int pPrio){
        if(pPrio>=0 && pPrio<=3 && !inList(pBeschreibung)){
            toDoList.append(new ToDo(pBeschreibung, pPrio));  
        }
    }
    
    public void removeToDo(String pBeschreibung){
        toDoList.toFirst();
        while(toDoList.hasAccess()){
            if(toDoList.getContent().getBeschreibung().equals(pBeschreibung)){
                toDoList.remove();
                break;
            }
            toDoList.next();
        }
    }
    
    public void removeToDo(int pIndex){
        if(pIndex>-1){
            toDoList.toFirst();
            int i= 0;       
            while(i<pIndex && toDoList.hasAccess()){            
                toDoList.next();
                i++;
            }
            if(toDoList.hasAccess()){
                toDoList.remove();
            }
        }
    }

}
