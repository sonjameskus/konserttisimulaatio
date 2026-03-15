package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class MenuGUI extends Application {

    @Override
    public void start(Stage menuStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu_view.fxml"));
        Parent root = fxmlLoader.load();

        menuStage.setTitle("Konserttisimulaattori");
        menuStage.setScene(new Scene(root));
        menuStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}