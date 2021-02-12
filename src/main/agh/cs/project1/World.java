package agh.cs.project1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class World extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root = (AnchorPane) loader.load(getClass().getResource("gui/AnimalSimGui.fxml").openStream());
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String [] args){
        launch(args);
   }
}
