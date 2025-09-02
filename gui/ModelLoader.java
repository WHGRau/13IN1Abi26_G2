package gui;


/**
 * Write a description of class ModelLoader here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public  class ModelLoader
{
    // instance variables - replace the example below with your own
    private static Model model = new Model();

    /**
     * Constructor for objects of class ModelLoader
     */
    public ModelLoader()
    {
        // initialise instance variables
        model = new Model();
    }
    
    public static Model getModel(){
        return model;
    }

}
