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

/**
 * Handles interfacing between the GUI and the model.
 */
public class MenuController {

    /**
     * TextField that takes the value for the amount of GA-Customers.
     */
    @FXML private TextField gaCustomerField;
    /**
     * TextField that takes the value for the amount of VIP-Customers.
     */
    @FXML private TextField vipCustomerField;
    /**
     * TextField that takes the value for the amount of workers in GA-Security.
     */
    @FXML private TextField gaSecurityField;
    /**
     * TextField that takes the value for the amount of workers in VIP-Security.
     */
    @FXML private TextField vipSecurityField;
    /**
     * TextField that takes the value for the amount of workers in GA-Cloakroom.
     */
    @FXML private TextField gaCloakroomField;
    /**
     * TextField that takes the value for the amount of workers in VIP-Cloakroom.
     */
    @FXML private TextField vipCloakroomField;
    /**
     * TextField that takes the value for the amount of workers in Merch.
     */
    @FXML private TextField merchField;
    /**
     * TextField that takes the value for the duration of the simulation.
     */
    @FXML private TextField simDurationField;
    /**
     * Button that starts the simulation.
     */
    @FXML private Button simStartButton;

    /**
     * Variable that records the amount of GA-Customers.
     */
    int gaCustomers = 0;
    /**
     * Variable that records the amount of VIP-Customers.
     */
    int vipCustomers = 0;
    /**
     * Variable that records the amount of workers in GA-Security.
     */
    int gaSecurity = 0;
    /**
     * Variable that records the amount of workers in VIP-Security.
     */
     int vipSecurity = 0;
    /**
     * Variable that records the amount of workers in GA-Cloakroom.
     */
    int gaCloakroom = 0;
    /**
     * Variable that records the amount of workers in VIP-Cloakroom.
     */
    int vipCloakroom = 0;
    /**
     * Variable that records the amount of workers in Merch.
     */
    int merch = 0;
    /**
     * Variable that records the duration of the simulation.
     */
    int simDuration = 0;

    /**
     * Submits the values given in the MenuGUI to the simulation.
     * @param actionEvent ActionEvent for Submit-button click.
     */
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
            simStartButton.setText("Tekstikenttiin pitaa antaa numeroita");
            return;
        }
        if (!simStartButton.isDisable()) {
            simStartButton.setText("Kaikki ok! :)");
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
                    System.out.println("Simulaatio on paattynyt.");

                    int total = controller.gaKävijämäärä + controller.vipKävijämäärä;
                    if (Customer.getPääsiSaliin() == total) {
                        System.out.println("Kaikki asiakkaat ehtivat konserttisaliin ajoissa.");
                    } else {
                        System.out.println("Kaikki asiakkaat eivat ehtineet konserttisaliin ajoissa.");
                        System.out.println("Saliin paasi " + Customer.getPääsiSaliin() + " / " + total);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                simulation.Database.getServiceAnalysis();;

            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Main arguments.
     */
    public static void main(String[] args) {
        MenuGUI.launch(MenuGUI.class);
    }
}