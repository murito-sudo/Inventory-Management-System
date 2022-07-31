package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;

/*
Future enhancements on main method
 */


public class HelloApplication extends Application {


    /**
     * RUNTIME ERROR: InvocationTargetException, caused by adding a non-existing fxml file to the fxmloader, it is easily corrected by
     * adding an existing fxml file
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     *
     * @param args
     * FUTURE ENHANCEMENT: Adding a database to the application will help the application data retrieval faster instead of using a linear
     * data structure, it will also store the parts and products that we add so we don't need to re-add everything from scratch.
     */
    public static void main(String[] args) {
        launch();
    }
}