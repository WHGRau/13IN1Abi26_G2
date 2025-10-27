package gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.scene.control.Label;

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
     * Scene1 (Anmeldung)
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
    private TextField textFieldBenutzername;

    @FXML
    private TextField textFieldPasswort;
    
    @FXML
    private Button buttonScene1;
    
    @FXML
    private Label anmeldenInfoFeld;

    
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
    
    public void switchToScene(ActionEvent event, String sceneName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scenes/" + sceneName +".fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            initialize();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    @FXML
    public void anmeldenButtonGedrueckt(ActionEvent event) {
        System.out.println("VERSCUUCH");
        String benutzername = textFieldBenutzername.getText();
        String passwort = textFieldPasswort.getText();
        LoginHandler.anmelden(benutzername, passwort);
        if (LoginHandler.istAngemeldet()) {
            switchToScene(event, "start");
        }
        else {
            anmeldenInfoFeld.setText("Anmeldedaten falsch. Bitte erneut versuchen.");
        }
    }
    
    @FXML
    public void registrierenButtonGedrueckt(ActionEvent event) {
        switchToScene(event, "scene2");
    }
    
    /* -----------------------
     * SCENE2 (Resgistrierung)
     * -----------------------
     */
    
    @FXML
    private TextField nameFeld2;
    
    @FXML
    private TextField mailFeld2;
    
    @FXML
    private TextField passwortFeld2;
    
    @FXML
    public void registrierenButtonGedrueckt2(ActionEvent event) {
        LoginHandler.registrieren(nameFeld2.getText(), mailFeld2.getText(), passwortFeld2.getText());
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
    
}

