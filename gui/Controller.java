package gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

// Imports für GUI Komponenten
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

// Imports für Tableview
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Imports für Scenenwechsel
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Controller {
    
    /* ------------------------------
     * Elemente der Scene1  
     * ------------------------------
     */
    @FXML
    private TableView<ToDo> toDoTableView;    
    
    @FXML
    private TableColumn<ToDo, String> beschreibungColumn;

    @FXML
    private TableColumn<ToDo, Integer> prioColumn;
    
    @FXML
    private Button button1;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;
    
    @FXML
    private Button buttonScene1;
   
    /* ------------------------------
     * Elemente der Scene2  
     * ------------------------------
     */
    @FXML
    private Button buttonScene2;
     

    
    // Verbindung vom Controller zum Model   
    private Model model; 
    
    public Controller(){
        model = ModelLoader.getModel();
        
    }
    
    public void initialize(){
        // Prüfen, ob scene1 geladen wird
        if(beschreibungColumn != null){
            // Vorbereitungen für scene1 - TableView Spalten vorbereiten
            beschreibungColumn.setCellValueFactory(new PropertyValueFactory<ToDo, String> ("beschreibung"));
            prioColumn.setCellValueFactory(new PropertyValueFactory<ToDo, Integer>("prioritaet"));
            tabelViewRefresh();
        }
    }

    @FXML
    void addToDo(ActionEvent event) {
        
        String beschreibung = textField1.getText();
        int    prio         = Integer.parseInt(textField2.getText()); 
        model.addToDo(beschreibung, prio);
      
        tabelViewRefresh();       
    }
    
    // Hilfsmethode für die Tableview
    private void tabelViewRefresh(){
        ArrayList<ToDo> toDoList = convertToArrayList(model.getToDoList());      
        ObservableList<ToDo> data = FXCollections.observableArrayList(toDoList);
        toDoTableView.setItems(data);
    }
    
    // Hilfsmethode für die Tableview, wird nicht benötigt, wenn mit ArrayLists gearbeitet wird.
    private ArrayList<ToDo> convertToArrayList(List<ToDo> pList){
        ArrayList<ToDo> ausgabe = new ArrayList<ToDo>();
        pList.toFirst();
        while(pList.hasAccess()){            
            ausgabe.add(pList.getContent());
            pList.next();
        }
        return ausgabe;
    }
    
    @FXML
    void tableViewClicked(MouseEvent event) {
        int selectedID = toDoTableView.getSelectionModel().getSelectedIndex();
        model.removeToDo(selectedID);
        tabelViewRefresh();
    }

    @FXML
    public void switchtoScene1(ActionEvent event) throws IOException{      
        Parent root = FXMLLoader.load(getClass().getResource("scenes/scene1.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        initialize();
    }
    
    @FXML
    public void switchtoScene2(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/scene2.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }     
    
}

