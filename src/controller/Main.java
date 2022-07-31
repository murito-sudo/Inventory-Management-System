package controller;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;

public class Main {


    public static Inventory inventory = new Inventory();
    public static int paID = 3;
    public static int prID = 10000;




    @FXML
    private Label welcomeText;
    @FXML
    private Label errorLabel;
    @FXML
    private Button exitButton;



    @FXML
    private TableView partView;

    @FXML
    private TableView productView;


    @FXML
    private TextField partSearch;
    @FXML
    private TextField productSearch;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partLevel;

    @FXML
    private TableColumn<Part, Double> partPrice;



    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productLevel;

    @FXML
    private TableColumn<Product, Double> productPrice;




    @FXML
    private void exitApp(){
        Platform.exit();
    }



    @FXML
    private void addProduct(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
            Stage stage = new Stage();


            stage.setTitle("Add Part");
            errorLabel.setVisible(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            System.out.println(e.getCause());
        }
    }


    @FXML
    private void modifyProduct(){
        Product t = (Product) productView.getSelectionModel().getSelectedItem();

        if(t != null){
            try {


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();

                stage.setTitle("Modify Part");
                errorLabel.setVisible(false);
                stage.setScene(new Scene(root));
                stage.show();


                Product out = (Product) productView.getSelectionModel().getSelectedItem();
                ModifyProduct md = fxmlLoader.getController();
                md.populateField(out);


                stage.show();

            }catch(Exception e){
                System.out.println(e);
            }

        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("You must select a product in order to modify it");
        }

    }



    @FXML
    private void deleteProduct(){
        if(productView.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("Select a product to delete");
        }else{
            boolean q = inventory.deleteProduct((Product) productView.getSelectionModel().getSelectedItem());
            if(q == false){
                errorLabel.setVisible(true);
                errorLabel.setText("You can't delete a product that has parts associated in it");
            }else{
                errorLabel.setVisible(true);
                errorLabel.setText("Product deleted successfully");
            }
        }


    }



    @FXML
    private void addPart(){

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Add Part");
            errorLabel.setVisible(false);
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    @FXML
    private void modifyPart(){

         Part t = (Part) partView.getSelectionModel().getSelectedItem();

        if(t != null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();

                stage.setTitle("Modify Part");
                errorLabel.setVisible(false);
                stage.setScene(new Scene(root));
                stage.show();

                if(partView.getSelectionModel().getSelectedItem().getClass().getSimpleName().equals("InHouse")){
                    InHouse in = (InHouse) partView.getSelectionModel().getSelectedItem();
                    ModifyPart md = fxmlLoader.getController();
                    md.populateField(in);


                }else{
                    Outsourced out = (Outsourced) partView.getSelectionModel().getSelectedItem();
                    ModifyPart md = fxmlLoader.getController();
                    md.populateField(out);




                }

                stage.show();

            }catch(Exception e){
                System.out.println(e);
            }

        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("You must select a part in order to modify it");
        }


    }



    @FXML
    private void deletePart(){
        Part t = (Part) partView.getSelectionModel().getSelectedItem();
        if(t != null){
            Main.inventory.deletePart(t);
            errorLabel.setVisible(true);
            errorLabel.setText("Part deleted successfully");
        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("Select a part to delete");
        }


    }



    @FXML
    private void initialize(){
        inventory.addPart(new InHouse(1, "Motor", 4.3, 5, 4, 9, 1001));
        inventory.addPart(new InHouse(2, "Wheel", 4.3, 5, 4, 9, 1001));
        partId.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partLevel.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productLevel.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));

        FilteredList<Part> filteredPart = new FilteredList<>(Main.inventory.getAllParts(), s -> true);
        FilteredList<Product> filteredProduct = new FilteredList<>(Main.inventory.getAllProducts(), s -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        partSearch.textProperty().addListener(obs->{
            String filter = partSearch.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredPart.setPredicate(s -> true);
                errorLabel.setVisible(false);

            }
            else {
                filteredPart.setPredicate(s -> String.valueOf(s.getId()).toLowerCase().contains(filter) || s.getName().toLowerCase().contains(filter));
                partView.setItems(filteredPart);
                if(filteredPart.size() == 0){
                    errorLabel.setVisible(true);
                    errorLabel.setText("No part(s) found");
                }else{
                    errorLabel.setVisible(false);
                }
            }
        });


        productSearch.textProperty().addListener(obs->{
            String filter = productSearch.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredProduct.setPredicate(s -> true);
                errorLabel.setVisible(false);
            }
            else {
                filteredProduct.setPredicate(s -> String.valueOf(s.getId()).toLowerCase().contains(filter) || s.getName().toLowerCase().contains(filter));
                productView.setItems(filteredProduct);
                if(filteredProduct.size() == 0){
                    errorLabel.setVisible(true);
                    errorLabel.setText("No product(s) found");
                }else{
                    errorLabel.setVisible(false);
                }
            }
        });

        partView.setItems(inventory.getAllParts());
        productView.setItems(inventory.getAllProducts());

    }


}