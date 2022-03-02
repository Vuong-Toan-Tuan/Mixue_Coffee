/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eProject2;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
//        Pane root = FXMLLoader.load(getClass().getResource("Home.fxml"));
//        
//        Scene scene = new Scene(root);
//        primaryStage.setTitle("Coffe Login");
//        primaryStage.setScene(scene);
//        primaryStage.show();

        String windowsTitle = "The Coffee";
        primaryStage.setTitle(windowsTitle);
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().goToLogin();
        Navigator.getInstance().getStage().show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
