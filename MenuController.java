package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simulation.Controller;
import simulation.Customer;
import view.MenuGUI;
import view.SimGUI;
import javafx.application.Platform;

public class MenuController {

    @FXML private TextField gaCustomerField;
    @FXML private TextField vipCustomerField;
    @FXML private TextField gaSecurityField;
    @FXML private TextField vipSecurityField;
    @FXML private TextField gaCloakroomField;
    @FXML private TextField vipCloakroomField;
    @FXML private TextField merchField;
    @FXML private TextField simDurationField;
    @FXML private Button simStartButton;

    int gaCustomers = 0;    int vipCustomers = 0;
    int gaSecurity = 0;     int vipSecurity = 0;
    int gaCloakroom = 0;    int vipCloakroom = 0;
    int merch = 0;          int simDuration = 0;

    @FXML
    public void submit(ActionEvent actionEvent) {

        try {
            gaCustomers = Integer.parseInt(gaCustomerField.getText());
            vipCustomers = Integer.parseInt(vipCustomerField.getText());
            gaSecurity = Integer.parseInt(gaSecurityField.getText());
            vipSecurity = Integer.parseInt(vipSecurityField.getText());
            gaCloakroom = Integer.parseInt(gaCloakroomField.getText());
            vipCloakroom = Integer.parseInt(vipCloakroomField.getText());
            merch = Integer.parseInt(merchField.getText());
            simDuration = Integer.parseInt(simDurationField.getText());
        } catch (NumberFormatException e) {
            simStartButton.setText("Anna vain numeroita!");
            return;
        }

        try {
            Platform.runLater(() -> {
                try {
                    new SimGUI().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            new Thread(() -> {
                try {
                    Controller controller = new Controller(
                            simDuration,
                            gaCustomers,
                            vipCustomers,
                            vipSecurity,
                            gaSecurity,
                            vipCloakroom,
                            gaCloakroom,
                            merch
                    );


                    if (controller.vipAsiakasmäärä < controller.vipKävijämäärä) {
                        controller.eventList.add(controller.entry.moveQueue(true));
                        controller.vipAsiakasmäärä++;
                    }
                    if (controller.gaAsiakasmäärä < controller.gaKävijämäärä) {
                        controller.eventList.add(controller.entry.moveQueue(false));
                        controller.gaAsiakasmäärä++;
                    }

                    controller.run();
                    System.out.println("Simulaatio on päättynyt.");

                    int total = controller.gaKävijämäärä + controller.vipKävijämäärä;
                    if (Customer.getPääsiSaliin() == total) {
                        System.out.println("Kaikki asiakkaat ehtivät konserttisaliin ajoissa.");
                    } else {
                        System.out.println("Kaikki asiakkaat eivät ehtineet konserttisaliin ajoissa.");
                        System.out.println("Saliin pääsi " + Customer.getPääsiSaliin() + " / " + total);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MenuGUI.launch(MenuGUI.class);
    }
}