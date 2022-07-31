package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;

public class AddPart {



    @FXML
    Button closeButton;
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
     TextField machineField;
    @FXML
    TextField minField;

    @FXML
    RadioButton inRadio;

    @FXML
    RadioButton outRadio;

    @FXML
    Label disLabel;

    @FXML
    Label errorLabel;


    @FXML
    private void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    private void check(ActionEvent event){

        try{
            if(event.getSource() == inRadio){
                outRadio.setSelected(false);
                inRadio.setSelected(true);
                disLabel.setText("Machine ID");
            }else{
                outRadio.setSelected(true);
                inRadio.setSelected(false);
                disLabel.setText("Company Name");
            }

        }catch(Exception e){
            System.out.println(e);
        }




    }
    @FXML
    private void savePart(){
        if( !nameField.getText().trim().isEmpty() && !invField.getText().trim().isEmpty()
        && !priceField.getText().trim().isEmpty() && !maxField.getText().trim().isEmpty() && !machineField.getText().trim().isEmpty()
        && !minField.getText().trim().isEmpty()){
                try {



                    if (Integer.parseInt(minField.getText()) <= Integer.parseInt(maxField.getText()) &&
                    Integer.parseInt(minField.getText()) <= Integer.parseInt(invField.getText()) &&
                            Integer.parseInt(maxField.getText()) >= Integer.parseInt(invField.getText())) {
                        if (inRadio.isSelected()) {
                            InHouse in = new InHouse(Main.paID, nameField.getText(),
                                    Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()),
                                    Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()),
                                    Integer.parseInt(machineField.getText()));
                            Main.inventory.addPart(in);

                        } else if (outRadio.isSelected()) {
                            Outsourced out = new Outsourced(Main.paID, nameField.getText(),
                                    Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()),
                                    Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()), machineField.getText());
                            Main.inventory.addPart(out);

                        }
                        Main.paID++;
                        Stage stage = (Stage) idField.getScene().getWindow();
                        stage.close();

                    } else {
                        System.out.println("Max value is less than Min value");
                        errorLabel.setVisible(true);
                        errorLabel.setText("Max  >= Min and Max >= Inv and Inv >= Min");
                    }
                }catch(Exception e){
                    errorLabel.setVisible(true);
                    errorLabel.setText("Format is invalid");
                    System.out.println(e);
                }


        }else{

            errorLabel.setVisible(true);
            errorLabel.setText("One of the field(s) is empty");
        }

    }
}
