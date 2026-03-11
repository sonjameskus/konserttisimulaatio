package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.MenuGUI;

import java.util.ArrayList;

public class MenuController {

    @FXML private TextField gaCustomerField;    @FXML  private TextField vipCustomerField;
    @FXML private TextField gaSecurityField;    @FXML private TextField vipSecurityField;
    @FXML private TextField gaCloakroomField;   @FXML private TextField vipCloakroomField;
    @FXML private TextField merchField;         @FXML private TextField simDurationField;
    @FXML private Button simStartButton;

    int gaCustomers = 0;    int vipCustomers = 0;
    int gaSecurity = 0;     int vipSecurity = 0;
    int gaCloakroom = 0;    int vipCloakroom = 0;
    int merch = 0;          int simDuration = 0;

    public void submit(ActionEvent actionEvent) {
        ArrayList<String> inputValues = new ArrayList<>();
        inputValues.add(gaCustomerField.getText());     inputValues.add(vipCustomerField.getText());
        inputValues.add(gaSecurityField.getText());     inputValues.add(vipSecurityField.getText());
        inputValues.add(gaCloakroomField.getText());    inputValues.add(vipCloakroomField.getText());
        inputValues.add(merchField.getText());          inputValues.add(simDurationField.getText());
        ArrayList<Integer> outputValues = new ArrayList<>();

        try {
            gaCustomers = Integer.parseInt(gaCustomerField.getText());
            vipCustomers = Integer.parseInt(vipCustomerField.getText());
            gaSecurity = Integer.parseInt(gaSecurityField.getText());
            vipSecurity = Integer.parseInt(vipSecurityField.getText());
            gaCloakroom = Integer.parseInt(gaCloakroomField.getText());
            vipCloakroom = Integer.parseInt(vipCloakroomField.getText());
            merch = Integer.parseInt(merchField.getText());
            simDuration = Integer.parseInt(simDurationField.getText());
        }
        catch (NumberFormatException e) {
            simStartButton.setDisable(true);
            simStartButton.setText("Tekstikenttiin pitää antaa numeroita");
        }
        if (!simStartButton.isDisable()) {
            simStartButton.setText("Kaikki ok! :)");


            Stage stage = new Stage();
        }
        simStartButton.setDisable(false);
    }

    public static void main(String[] args) {
        MenuGUI.launch(MenuGUI.class);
    }
}