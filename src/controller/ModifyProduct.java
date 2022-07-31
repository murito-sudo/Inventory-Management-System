package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import model.Product;
import model.Part;
import model.InHouse;
import model.Outsourced;

public class ModifyProduct {


    private ObservableList<Part> p;
    @FXML
    TextField idField;

    @FXML
    TextField nameField;
    @FXML
    TextField invField;
    @FXML
    TextField priceField;
    @FXML
    TextField maxField;
    @FXML
    TextField minField;

    @FXML
    TextField partSearch;


    @FXML
    Label errorLabel;



    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partLevel;

    @FXML
    private TableColumn<Part, Double> partPrice;


    @FXML
    private TableColumn<Part, Integer> partId2;

    @FXML
    private TableColumn<Part, String> partName2;

    @FXML
    private TableColumn<Part, Integer> partLevel2;

    @FXML
    private TableColumn<Part, Double> partPrice2;


    @FXML
    private TableView productView;

    @FXML
    private TableView productView2;



    /**
     * RUNTIME ERROR: NullPointerException, it is caused if one of the field variables does/do not exist, it is solved by using the appropiate
     * variables in this class, and also checking if the parameter we are passing is not null.
     * @param t
     */
    @FXML
    public void populateField(Product t) throws RuntimeException{

        if(t != null){
            idField.setText(String.valueOf(t.getId()));
            nameField.setText(t.getName());
            invField.setText(String.valueOf(t.getStock()));
            priceField.setText(String.valueOf(t.getPrice()));
            maxField.setText(String.valueOf(t.getMax()));
            minField.setText(String.valueOf(t.getMin()));


            partId2.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
            partName2.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
            partLevel2.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
            partPrice2.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));

            p = t.getAllAssociatedPart();
            productView2.setItems(p);

        }



    }



    @FXML
    private void addAssociatedPart(){

        if(productView.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("You must select one part to add");

        }else{
            if(productView.getSelectionModel().getSelectedItem().getClass().getSimpleName().equals("InHouse")){
                p.add((InHouse) productView.getSelectionModel().getSelectedItem());
            }else{
                p.add((Outsourced) productView.getSelectionModel().getSelectedItem());
            }
            errorLabel.setVisible(true);
            errorLabel.setText("Part added succesfully");
        }


    }

    @FXML
    private void removeAssociatedPart(){
        if(productView2.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("You must select an associated part to delete");
        }

        for(int x = 0; x < p.size(); x++){
            Part t = (Part) productView2.getSelectionModel().getSelectedItem();
            if(t == null){
                continue;
            }
            if(String.valueOf(p.get(x).getId())
                    .equals(String.valueOf(t.getId()))){
                p.remove(x);
                errorLabel.setVisible(true);
                errorLabel.setText("Associated part removed succesfully");
                break;
            }
        }
    }



    @FXML
    private void updateProduct(){
        if( !nameField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty()
                && !invField.getText().trim().isEmpty() && !minField.getText().trim().isEmpty() && !maxField.getText().trim().isEmpty()){

            try{



                if(Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText()) ||
                        Integer.parseInt(minField.getText()) > Integer.parseInt(invField.getText()) ||
                        Integer.parseInt(maxField.getText()) < Integer.parseInt(invField.getText())){

                    errorLabel.setVisible(true);
                    errorLabel.setText("Max  >= Min and Max >= Inv and Inv >= Min");
                    return;
                }



                for(int x = 0; x < Main.inventory.getAllProducts().size(); x++){
                    if(String.valueOf(Main.inventory.getAllProducts().get(x).getId()).equals(idField.getText())){
                        Product p = new Product(Integer.parseInt(idField.getText()), nameField.getText(), Double.parseDouble(priceField.getText())
                        , Integer.parseInt(invField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()));
                        p.associatedParts = this.p;
                        Main.inventory.updateProduct(x, p);
                        break;

                    }
                }

                Stage stage = (Stage)this.productView.getScene().getWindow();
                stage.close();

            }catch(Exception e){
                errorLabel.setVisible(true);
                errorLabel.setText("Invalid format");
            }




        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("One of the field(s) is empty");
        }
    }





    @FXML
    private void close(){
        Stage stage = (Stage)this.productView.getScene().getWindow();
        stage.close();
    }



    @FXML
    private void initialize(){
        partId.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partLevel.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));

        FilteredList<Part> filteredPart = new FilteredList<>(Main.inventory.getAllParts(), s -> true);

        partSearch.textProperty().addListener(obs->{
            String filter = partSearch.getText().toLowerCase();
            if(filter == null || filter.length() == 0) {
                filteredPart.setPredicate(s -> true);
                errorLabel.setVisible(false);
            }
            else {
                filteredPart.setPredicate(s -> String.valueOf(s.getId()).toLowerCase().contains(filter) || s.getName().toLowerCase().contains(filter));
                productView.setItems(filteredPart);
                if(filteredPart.size() == 0){
                    errorLabel.setVisible(true);
                    errorLabel.setText("No product(s) found");
                }else{
                    errorLabel.setVisible(false);
                }
            }
        });

        productView.setItems(Main.inventory.getAllParts());


    }
}
