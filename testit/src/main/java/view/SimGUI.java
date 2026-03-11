package view;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SimGUI extends Application {

    @Override
    public void start(Stage simStage) throws Exception {
        int offsetX = 400;
        int offsetY = 30;
        int boxWidth = 150;
        int boxHeight = 270;
        Canvas simCanvas = new Canvas(1280,720);

        GraphicsContext simGC = simCanvas.getGraphicsContext2D();

        simGC.setFill(Color.color(.6, .5, 1));
        simGC.fillRect((int)(simCanvas.getWidth()/2-offsetX-(boxWidth/2)), (int)(simCanvas.getHeight()/2+offsetY), boxWidth, boxHeight);
        simGC.fillRect((int)(simCanvas.getWidth()/2-offsetX-(boxWidth/2)), (int)(simCanvas.getHeight()/2-305), boxWidth, boxHeight);
        simGC.setFill(Color.color(1, .7, .4));
        simGC.fillRect((int)(simCanvas.getWidth()/2-(boxWidth/2)), (int)(simCanvas.getHeight()/2+offsetY), boxWidth, boxHeight);
        simGC.fillRect((int)(simCanvas.getWidth()/2-(boxWidth/2)), (int)(simCanvas.getHeight()/2-305), boxWidth, boxHeight);
        simGC.setFill(Color.color(.5, 1, .5));
        simGC.fillRect((int)(simCanvas.getWidth()/2+offsetX-(boxWidth/2)), (int)(simCanvas.getHeight()/2-305), boxWidth, ((boxHeight*2)+(offsetY*2)));
        simGC.setFill(Color.color(.5, .5, .5));
        simGC.fillRect((int)(simCanvas.getWidth()-25), (int)(simCanvas.getHeight()/2-305), 25, ((boxHeight*2)+(offsetY*2)));
        simGC.fillRect(0, (int)(simCanvas.getHeight()/2+offsetY), 25, boxHeight);
        simGC.fillRect(0, (int)(simCanvas.getHeight()/2-305), 25, boxHeight);

        simGC.setFill(Color.color(.3, .3, .3));
        simGC.setFont(new Font(simGC.getFont().getStyle(), 48));
        simGC.setTextAlign(TextAlignment.CENTER);
        simGC.setTextBaseline(VPos.BOTTOM);
        simGC.fillText("General Access -Asiakkaat",(int)(simCanvas.getWidth()/2),(int)(simCanvas.getHeight()/2-boxHeight-offsetY));
        simGC.setTextBaseline(VPos.TOP);
        simGC.fillText("VIP -Asiakkaat",(int)(simCanvas.getWidth()/2),(int)(simCanvas.getHeight()/2+boxHeight+offsetY));
        simGC.setFont(new Font(simGC.getFont().getStyle(), 24));
        simGC.setTextBaseline(VPos.CENTER);
        simGC.fillText("Turvatarkastus",(int)(simCanvas.getWidth()/2-offsetX),(int)(simCanvas.getHeight()/2));
        simGC.fillText("Narikka",(int)(simCanvas.getWidth()/2),(int)(simCanvas.getHeight()/2));
        simGC.fillText("Oheistuotemyymälä",(int)(simCanvas.getWidth()/2+offsetX),(int)(simCanvas.getHeight()/2));


        StackPane simPane = new StackPane();
        simPane.getChildren().add(simCanvas);
        Scene simScene = new Scene(simPane,1280,720);
        simStage.setTitle("Konserttisimulaattori");
        simStage.setResizable(false);
        simStage.setScene(simScene);
        simStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
