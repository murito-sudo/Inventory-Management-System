package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;


public class ModifyPart {

    int index;
    static Part res;

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
    Label disLabel;

    @FXML
    Label errorLabel;

    @FXML
    RadioButton inRadio;
    @FXML
    RadioButton outRadio;






    /**
     * RUNTIME ERROR: NullPointerException, it is caused if one of the field variables does/do not exist, it is solved by using the appropiate
     *variables in this class, and also checking if the parameter we are passing is not null.
     * @param t
     */
    @FXML
    public void populateField(Part t) throws RuntimeException{
        if(t != null){
            idField.setText(String.valueOf(t.getId()));
            nameField.setText(t.getName());
            invField.setText(String.valueOf(t.getStock()));
            priceField.setText(String.valueOf(t.getPrice()));
            maxField.setText(String.valueOf(t.getMax()));
            minField.setText(String.valueOf(t.getMin()));

            if(t.getClass().getSimpleName().equals("InHouse")){
                InHouse n = (InHouse) t;
                inRadio.setSelected(true);
                outRadio.setSelected(false);
                machineField.setText(String.valueOf(n.getMachineId()));
            }else{
                Outsourced n = (Outsourced) t;
                inRadio.setSelected(false);
                outRadio.setSelected(true);
                machineField.setText(n.getCompanyName());
                disLabel.setText("Company Name");

            }

        }



    }

    @FXML
    private void closeWindow(){
        Stage stage = (Stage) nameField.getScene().getWindow();
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
    private void updatePart(){
        if(!nameField.getText().trim().isEmpty() && !invField.getText().trim().isEmpty()
                && !priceField.getText().trim().isEmpty() && !maxField.getText().trim().isEmpty() && !machineField.getText().trim().isEmpty()
                && !minField.getText().trim().isEmpty()){

            try{




                if(Integer.parseInt(minField.getText()) <= Integer.parseInt(maxField.getText()) &&
                        Integer.parseInt(minField.getText()) <= Integer.parseInt(invField.getText()) &&
                        Integer.parseInt(maxField.getText()) >= Integer.parseInt(invField.getText())) {
                    if (inRadio.isSelected()) {
                        InHouse in = new InHouse(Integer.parseInt(idField.getText()), nameField.getText(),
                                Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()),
                                Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()),
                                Integer.parseInt(machineField.getText()));

                        for(int x = 0; x < Main.inventory.getAllParts().size(); x++){

                            if(String.valueOf(Main.inventory.getAllParts().get(x).getId()).equals(idField.getText())){
                                Main.inventory.updatePart(x, in);
                                break;
                            }

                        }

                    } else if (outRadio.isSelected()) {
                        Outsourced out = new Outsourced(Integer.parseInt(idField.getText()), nameField.getText(),
                                Double.parseDouble(priceField.getText()), Integer.parseInt(invField.getText()),
                                Integer.parseInt(minField.getText()), Integer.parseInt(maxField.getText()), machineField.getText());


                        for(int x = 0; x < Main.inventory.getAllParts().size(); x++){

                            if(String.valueOf(Main.inventory.getAllParts().get(x).getId()).equals(idField.getText())){
                                Main.inventory.updatePart(x, out);
                                break;
                            }

                        }

                    }

                    Stage stage = (Stage) nameField.getScene().getWindow();
                    stage.close();

                }else{

                    errorLabel.setVisible(true);
                    errorLabel.setText("Max  >= Min and Max >= Inv and Inv >= Min");
                }

            }catch(Exception e){
                errorLabel.setVisible(true);
                errorLabel.setText("Format is invalid");
                System.out.println("One of the field(s) is invalid");
            }

        }else{
            errorLabel.setVisible(true);
            errorLabel.setText("One of the field(s) is empty");
        }

    }

}
