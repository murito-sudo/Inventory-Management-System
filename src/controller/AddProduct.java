package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;

public class AddProduct {



    private ObservableList<Part> p = FXCollections.observableArrayList();


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
    private TableView partView;

    @FXML
    private TableView partView2;


    /**
     * RUNTIME ERROR: Null.Pointer.Exception, this happens if you try to invoke this method without selecting any element on the partview
     * fixed by using a conditional statement to check if the selected item is null or not.
     */
    @FXML
    public void addAssociatedPart() throws Exception{



        if(partView.getSelectionModel().getSelectedItem() != null){
            if(partView.getSelectionModel().getSelectedItem().getClass().getSimpleName().equals("InHouse")){
                p.add((InHouse) partView.getSelectionModel().getSelectedItem());
            }else{
                p.add((Outsourced) partView.getSelectionModel().getSelectedItem());
            }
            errorLabel.setVisible(true);
            errorLabel.setText("Part added succesfully");
        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("You must select a part to add");
        }



    }



    @FXML
    private void removeAssociatedPart(){
        if(partView2.getSelectionModel().getSelectedItem() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("You must select an associated part to delete");
        }

        for(int x = 0; x < p.size(); x++){
            Part t = (Part) partView2.getSelectionModel().getSelectedItem();
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


    /**
     *
     */
    @FXML
    private void addProduct(){

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


                Product t = new Product(Main.prID, nameField.getText(), Double.parseDouble(priceField.getText()),
                        Integer.parseInt(invField.getText()), Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()));
                t.associatedParts = p;

                Main.inventory.getAllProducts().add(t);
                Main.prID++;

                Stage stage = (Stage)this.partView.getScene().getWindow();
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
        Stage stage = (Stage)this.partView.getScene().getWindow();
        stage.close();
    }




    @FXML
    private void initialize(){
        partId.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partLevel.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));

        partId2.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
        partName2.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
        partLevel2.setCellValueFactory(new PropertyValueFactory<Part,Integer>("stock"));
        partPrice2.setCellValueFactory(new PropertyValueFactory<Part,Double>("price"));

        FilteredList<Part> filteredPart = new FilteredList<>(Main.inventory.getAllParts(), s -> true);

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
                    errorLabel.setText("No product(s) found");
                }else{
                    errorLabel.setVisible(false);
                }
            }
        });




        partView.setItems(Main.inventory.getAllParts());
        partView2.setItems(p);
    }
}
