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


                    if (controller.vipAsiakasmaara < controller.vipKavijamaara) {
                        controller.eventList.add(controller.entry.moveQueue(true));
                        controller.vipAsiakasmaara++;
                    }
                    if (controller.gaAsiakasmaara < controller.gaKavijamaara) {
                        controller.eventList.add(controller.entry.moveQueue(false));
                        controller.gaAsiakasmaara++;
                    }

                    controller.run();
                    System.out.println("Simulaatio on paattynyt.");

                    int total = controller.gaKavijamaara + controller.vipKavijamaara;
                    if (Customer.getPaasiSaliin() == total) {
                        System.out.println("Kaikki asiakkaat ehtivat konserttisaliin ajoissa.");
                    } else {
                        System.out.println("Kaikki asiakkaat eivat ehtineet konserttisaliin ajoissa.");
                        System.out.println("Saliin paasi " + Customer.getPaasiSaliin() + " / " + total);
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
     * @param args
     */
    public static void main(String[] args) {
        MenuGUI.launch(MenuGUI.class);
    }
}