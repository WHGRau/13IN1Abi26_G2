package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Controller {

    /* ------------------------------
     * Scene1 (Anmeldung)
     * ------------------------------
     */
    @FXML private TableView<ToDo> toDoTableView;
    @FXML private TableColumn<ToDo, String> beschreibungColumn;
    @FXML private TableColumn<ToDo, Integer> prioColumn;

    @FXML private Button button1;

    @FXML private TextField textFieldBenutzername;
    @FXML private TextField textFieldPasswort;

    @FXML private Button buttonScene1;

    @FXML private Label anmeldenInfoFeld;

    // Verbindung vom Controller zum Model
    private Model model;

    private Verwaltung restaurant;

    public Controller() {
        model = ModelLoader.getModel();
        restaurant = new Verwaltung();
    }

    public void initialize() {
        // scene1 – TableView vorbereiten
        if (beschreibungColumn != null) {
            beschreibungColumn.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
            prioColumn.setCellValueFactory(new PropertyValueFactory<>("prioritaet"));
        }

        if (halloLabelR != null && LoginHandler.angemeldetAls() != null) {
            halloLabelR.setText("Hallo, " + LoginHandler.angemeldetAls().getVorname());
        }

        // Reservierung: Standardjahr & Uhrzeiten
        if (jahrFeldRZ != null && (jahrFeldRZ.getText() == null || jahrFeldRZ.getText().isEmpty())) {
            jahrFeldRZ.setText(String.valueOf(Year.now().getValue()));
        }
        if (zeitComboRZ != null && zeitComboRZ.getItems().isEmpty()) {
            zeitComboRZ.getItems().setAll(
                "17:00","17:30","18:00","18:30",
                "19:00","19:30","20:00","20:30",
                "21:00","21:30"
            );
            zeitComboRZ.getSelectionModel().selectFirst();
        }

        // Meine Reservierungen: Tabelle konfigurieren
        if (meineResTableView != null) {
            colZeitpunkt.setCellValueFactory(new PropertyValueFactory<>("zeitpunkt"));
            colPersonen.setCellValueFactory(new PropertyValueFactory<>("personen"));

            // Button-Spalte "Löschen"
            colAktion.setCellFactory(new Callback<TableColumn<ReservierungRow, Void>, TableCell<ReservierungRow, Void>>() {
                @Override
                public TableCell<ReservierungRow, Void> call(TableColumn<ReservierungRow, Void> param) {
                    return new TableCell<>() {
                        private final Button btn = new Button("Löschen");
                        {
                            btn.setOnAction(evt -> {
                                ReservierungRow row = getTableView().getItems().get(getIndex());
                                loescheReservierung(row.getId());
                            });
                        }
                        @Override protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            setGraphic(empty ? null : btn);
                        }
                    };
                }
            });

            // Daten laden
            refreshMeineReservierungen();
        }
        
        if (mitarbeiterButton != null) {
            boolean isMitarbeiter = false;
            if (LoginHandler.angemeldetAls() != null) {
                int nutzerId = LoginHandler.angemeldetAls().getId();
                isMitarbeiter = istMitarbeiter(nutzerId);
            }
            mitarbeiterButton.setVisible(isMitarbeiter);
            mitarbeiterButton.setManaged(isMitarbeiter);
        }
        
        if (mitarbeiterTableView != null) {
            colNachname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nachname"));
            colTisch.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("tisch"));
            colUhrzeit.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("uhrzeit"));
            colPersonenzahl.setCellValueFactory(new PropertyValueFactory<>("personenzahl"));
            refreshMitarbeiterHeute();
        }
        
        // --- Restaurants-Übersicht ---
        if (restaurantTable != null) {
        
            restaurantNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
            );
            averageRatingColumn.setCellValueFactory(
                new PropertyValueFactory<>("avg")
            );
            notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("kommentar")
            );
        
            refreshRestaurants();
        }

    }


    @FXML
    void tableViewClicked(MouseEvent event) {
        int selectedID = toDoTableView.getSelectionModel().getSelectedIndex();
        model.removeToDo(selectedID);
    }

    public void switchToScene(ActionEvent event, String sceneName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scenes/" + sceneName + ".fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            // KEIN manuelles initialize() hier aufrufen!
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void anmeldenButtonGedrueckt(ActionEvent event) {
        String benutzername = textFieldBenutzername.getText();
        String passwort = textFieldPasswort.getText();
        if (benutzername.contains("'")) {
            anmeldenInfoFeld.setText("Keine einfachen Anführungszeichen verwenden!");
            return;
        }
        LoginHandler.anmelden(benutzername, passwort);
        if (LoginHandler.istAngemeldet()) {
            switchToScene(event, "start");
        } else {
            anmeldenInfoFeld.setText("Anmeldedaten falsch. Bitte erneut versuchen.");
        }
    }

    @FXML
    public void registrierenButtonGedrueckt(ActionEvent event) {
        switchToScene(event, "scene2");
    }

    /* -----------------------
     * SCENE2 (Registrierung)
     * -----------------------
     */

    @FXML private TextField nameFeld2;
    @FXML private TextField mailFeld2;
    @FXML private TextField passwortFeld2;

    private static String name2;
    private static String mail2;
    private static String passwort2;

    @FXML
    public void registrierenButtonGedrueckt2(ActionEvent event) {
        name2 = nameFeld2.getText();
        mail2 = mailFeld2.getText();
        passwort2 = passwortFeld2.getText();
        switchToScene(event, "registrierung");
    }

    @FXML
    public void switchToScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scenes/scene1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // KEIN manuelles initialize() hier aufrufen!
    }

    /* --------------
     * SCENE Registrierung
     * --------------
     */

    @FXML private TextField vornameFeldR;
    @FXML private TextField nachnameFeldR;

    @FXML
    public void registrierenButtonGedruecktR(ActionEvent event) {
        LoginHandler.registrieren(name2, mail2, passwort2, vornameFeldR.getText(), nachnameFeldR.getText());
        LoginHandler.anmelden(name2, passwort2);
        switchToScene(event, "start");
    }

    /*
     * -----------------------
     * SCENE Start
     * -----------------------
     */

    @FXML private Label halloLabelR;

    @FXML
    public void switchToSceneReservierung(ActionEvent e) {
        switchToScene(e, "restaurants");
    }

    @FXML
    public void switchToSceneMeineReservierungen(ActionEvent e) {
        switchToScene(e, "meineReservierungen");
    }

    /*
     * --------------------
     * SCENE Reservierung
     * --------------------
     */

    @FXML private TextField tagFeldRZ;
    @FXML private TextField monatFeldRZ;
    @FXML private TextField jahrFeldRZ;
    @FXML private ComboBox<String> zeitComboRZ;

    @FXML private TextField personenFeldRZ;
    @FXML private Label reservierungInfoFeld;

    @FXML
    public void reservierenJetztButtonGedrueckt(ActionEvent e) {
        if (reservierungInfoFeld != null) {
            reservierungInfoFeld.setText("");
            reservierungInfoFeld.setTextFill(javafx.scene.paint.Color.web("#9f0000"));
        }

        // Personenanzahl prüfen
        int personen;
        try {
            String pTxt = personenFeldRZ != null ? personenFeldRZ.getText().trim() : "";
            personen = Integer.parseInt(pTxt);
            if (personen <= 0) throw new NumberFormatException("<=0");
        } catch (NumberFormatException ex) {
            if (reservierungInfoFeld != null) reservierungInfoFeld.setText("Bitte eine positive Personenanzahl eingeben.");
            return;
        }

        // Datum prüfen
        int tt, mm, yyyy;
        try {
            tt = Integer.parseInt(tagFeldRZ.getText().trim());
            mm = Integer.parseInt(monatFeldRZ.getText().trim());
            yyyy = Integer.parseInt(jahrFeldRZ.getText().trim());
            // validiert tatsächliches Datum
            LocalDate.of(yyyy, mm, tt);
        } catch (Exception ex) {
            if (reservierungInfoFeld != null) reservierungInfoFeld.setText("Bitte ein gültiges Datum eingeben (TT/MM/JJJJ).");
            return;
        }

        // Uhrzeit prüfen
        String zeit = zeitComboRZ != null ? zeitComboRZ.getValue() : null;
        if (zeit == null || zeit.isEmpty()) {
            if (reservierungInfoFeld != null) reservierungInfoFeld.setText("Bitte eine Uhrzeit auswählen.");
            return;
        }

        // Zeitstring im richtigen Format (hier mit Leerzeichen; Restaurant parst "yyyy-MM-dd HH:mm")
        String datumStr = String.format("%04d-%02d-%02d", yyyy, mm, tt);
        String zeitpunktString = datumStr + " " + zeit;

        boolean ok = restaurant.reserviere(personen, zeitpunktString);
        if (ok) {
            if (reservierungInfoFeld != null) {
                reservierungInfoFeld.setText("Reservierung erfolgreich!");
                reservierungInfoFeld.setTextFill(javafx.scene.paint.Color.web("#008000"));
            }
        } else {
            if (reservierungInfoFeld != null) {
                reservierungInfoFeld.setText("Reservierung fehlgeschlagen, leider kein Tisch mehr frei");
            }
        }
    }

    @FXML
    public void switchToSceneStart(ActionEvent event) {
        switchToScene(event, "start");
    }

    /*
     * ----------------------------
     * SCENE Meine Reservierungen
     * ----------------------------
     */

    @FXML private TableView<ReservierungRow> meineResTableView;
    @FXML private TableColumn<ReservierungRow, String> colZeitpunkt;
    @FXML private TableColumn<ReservierungRow, Integer> colPersonen;
    @FXML private TableColumn<ReservierungRow, Void> colAktion;

    private final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private void refreshMeineReservierungen() {
        if (meineResTableView == null) return;
        if (LoginHandler.angemeldetAls() == null) {
            meineResTableView.setItems(FXCollections.observableArrayList());
            return;
        }
        int gastId = LoginHandler.angemeldetAls().getId();

        List<ReservierungRow> rows = new ArrayList<>();

        // Hole Reservierungen des eingeloggten Gastes
        List<Reservierung> liste = restaurant.listeReservierungenFuerGast(gastId);
        for (Reservierung r : liste) {
            // Annahme: Reservierung hat getId(), getZeitpunkt() (LocalDateTime) und getPersonen()
            String z = r.getZeitpunkt().format(OUT);
            rows.add(new ReservierungRow(r.getId(), z, r.getPersonenzahl()));
        }

        meineResTableView.setItems(FXCollections.observableArrayList(rows));
    }

    private void loescheReservierung(int reservierungId) {
        // Reservierung stornieren/löschen
        boolean ok = restaurant.storniereReservierung(reservierungId);
        // Alternativ, falls Methode anders heißt:
        // boolean ok = restaurant.loescheReservierung(reservierungId);

        if (!ok) {
            System.err.println("Stornierung fehlgeschlagen für ID: " + reservierungId);
        }
        refreshMeineReservierungen();
    }
    
    // 1) --- Feld hinzufügen (bei den anderen @FXML-Feldern) ---
    @FXML private Button mitarbeiterButton;
    
    // 3) --- Handler für den Button (irgendwo bei deinen @FXML-Handlern platzieren) ---
    @FXML
    public void switchToSceneMitarbeiter(javafx.event.ActionEvent e) {
        // Erwartet eine Datei: gui/scenes/mitarbeiter.fxml
        switchToScene(e, "mitarbeiter");
    }
    
    // 4) --- Mitarbeiter-Check (private Methode in den Controller) ---
    private boolean istMitarbeiter(int nutzerId) {
        try {
            DatabaseConnector db = new DatabaseConnector("localhost", 3306, "restaurant_db", "root", "");
            db.executeStatement(String.format(
                "SELECT 1 FROM mitarbeiter WHERE nutzerId = %d LIMIT 1;", nutzerId
            ));
            QueryResult qr = db.getCurrentQueryResult();
            return qr != null && qr.getRowCount() > 0;
        } catch (Exception ex) {
            System.err.println("Mitarbeiter-Check fehlgeschlagen: " + ex.getMessage());
            return false;
        }
    }
    
    @FXML private TableView<MitarbeiterRow> mitarbeiterTableView;
    @FXML private TableColumn<MitarbeiterRow, String> colNachname;
    @FXML private TableColumn<MitarbeiterRow, Integer> colTisch;
    @FXML private TableColumn<MitarbeiterRow, String> colUhrzeit;
    @FXML private TableColumn<MitarbeiterRow, Integer> colPersonenzahl;
    
    public static class MitarbeiterRow {
        private final String nachname;
        private final int tisch;
        private final String uhrzeit; // "HH:mm"
        private final int personenzahl;
        public MitarbeiterRow(String nachname, int tisch, String uhrzeit, int personenzahl) {
            this.nachname = nachname; 
            this.tisch = tisch; 
            this.uhrzeit = uhrzeit;
            this.personenzahl = personenzahl;
        }
        public String getNachname() { return nachname; }
        public int getTisch() { return tisch; }
        public String getUhrzeit() { return uhrzeit; }
        public int getPersonenzahl() { return personenzahl; }
    }
    
    private void refreshMitarbeiterHeute() {
        if (mitarbeiterTableView == null) return;
    
        String heute = java.time.LocalDate.now().toString(); // yyyy-MM-dd für getReservierungenAnTag
        java.util.List<Reservierung> liste = restaurant.getReservierungenAnTag(heute);
    
        java.time.format.DateTimeFormatter tf = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
        java.util.List<MitarbeiterRow> rows = new java.util.ArrayList<>();
    
        for (Reservierung r : liste) {
            String nachname = restaurant.getNachnameVonBenutzerkonto(r.getGastId());
            int tischNummer = r.getTisch(); // falls du echte „Tischnummern“ statt IDs willst, hier ggf. lookup
            String uhrzeit = r.getZeitpunkt().format(tf);
            int personenzahl = r.getPersonenzahl();
            rows.add(new MitarbeiterRow(nachname, tischNummer, uhrzeit, personenzahl));
        }
    
        mitarbeiterTableView.setItems(javafx.collections.FXCollections.observableArrayList(rows));
    }

    // Datenklasse für TableView
    public static class ReservierungRow {
        private final int id;
        private final String zeitpunkt; // z. B. "29.10.2025 19:30"
        private final int personen;

        public ReservierungRow(int id, String zeitpunkt, int personen) {
            this.id = id; this.zeitpunkt = zeitpunkt; this.personen = personen;
        }
        public int getId() { return id; }
        public String getZeitpunkt() { return zeitpunkt; }
        public int getPersonen() { return personen; }
    }
    
    ///////////////////
    // RESTAURANT-ÜBERSICHT
    ///////////////////
    
    @FXML private TableView<RestaurantRow> restaurantTable;
    @FXML private TableColumn<RestaurantRow, String> restaurantNameColumn;
    @FXML private TableColumn<RestaurantRow, Double> averageRatingColumn;
    @FXML private TableColumn<RestaurantRow, String> notesColumn;
    
    @FXML private Label restaurantInfoLabel;

    public static class RestaurantRow {
    private final int id;
    private final String name;
    private final double avg;
    private final String kommentar;

    public RestaurantRow(int id, String name, double avg, String kommentar) {
        this.id = id;
        this.name = name;
        this.avg = avg;
        this.kommentar = kommentar;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getAvg() { return avg; }
    public String getKommentar() { return kommentar; }
}

    
    private void refreshRestaurants() {
        if (restaurantTable == null) return;
    
        List<Restaurant> liste = restaurant.getAlleRestaurants(); // musst du in Verwaltung implementieren
    
        List<RestaurantRow> rows = new ArrayList<>();
    
        for (Restaurant r : liste) {
    
            double avg = restaurant.getDurchschnittsBewertungFuerRestaurant(r.getId());
            String kommentar = "yummy"; // optional, kann auch leer sein
    
            rows.add(new RestaurantRow(
                r.getId(),
                r.getName(),
                avg,
                kommentar == null ? "" : kommentar
            ));
        }
    
        restaurantTable.setItems(FXCollections.observableArrayList(rows));
    }

    @FXML
    public void mehrInfosGedrueckt(ActionEvent e) {
        RestaurantRow row = restaurantTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            restaurantInfoLabel.setText("Bitte ein Restaurant auswählen.");
            return;
        }
    
        // später Navigation zu z. B. restaurantDetails.fxml
        // und Übergabe von row.getId()
        switchToScene(e, "restaurantDetails");
    }
    
    @FXML
    public void reservierenGedrueckt(ActionEvent e) {
        RestaurantRow row = restaurantTable.getSelectionModel().getSelectedItem();
        if (row == null) {
            restaurantInfoLabel.setText("Bitte ein Restaurant auswählen.");
            return;
        }
    
        // später Navigation zu reservierung.fxml mit vorausgewähltem Restaurant
        switchToScene(e, "reservierung");
    }

    
}

