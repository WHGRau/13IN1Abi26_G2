package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{       
        Parent root = FXMLLoader.load(getClass().getResource("scenes/scene1.fxml"));
        primaryStage.setTitle("ToDo-Liste");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}

