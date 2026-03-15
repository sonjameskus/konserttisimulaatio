package view;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import simulation.Controller;
import simulation.Customer;
import java.util.ArrayList;
import java.util.List;


public class SimGUI extends Application {
    private static GraphicsContext simGC;
    private static int hallCount = 0;
    private static List<Customer> customers = new ArrayList<>();

    public static List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void start(Stage simStage) throws Exception {
        int offsetX = 400;
        int offsetY = 30;
        int boxWidth = 150;
        int boxHeight = 270;

        Canvas simCanvas = new Canvas(1280, 720);
        simGC = simCanvas.getGraphicsContext2D();

        drawStatic(simGC);

        StackPane simPane = new StackPane(simCanvas);
        Scene simScene = new Scene(simPane, 1280, 720);
        simStage.setTitle("Konserttisimulaattori");
        simStage.setResizable(false);
        simStage.setScene(simScene);
        simStage.show();

        simScene.setOnKeyPressed(e -> {

            switch (e.getCode()) {

                case UP:
                    if (Controller.simulationSpeed > 5) {
                        Controller.simulationSpeed -= 5;
                    }
                    System.out.println("Simulaation nopeus: " + Controller.simulationSpeed);
                    break;

                case DOWN:
                    Controller.simulationSpeed += 5;
                    System.out.println("Simulaation nopeus: " + Controller.simulationSpeed);
                    break;
            }

        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
                for (Customer c : customers) {
                    c.moveStep();
                }
            }
        };
        timer.start();

    }
    public static void updateHallCount(int count) {
        hallCount = count;
    }

    public static void updateCustomer(Customer c) {
        if (!customers.contains(c)) {
            customers.add(c);
            //System.out.println("Customer added to GUI: " + c.getId());

        }
    }

    public static void draw() {
        if (simGC == null) return;
        simGC.clearRect(0, 0, 1280, 720);
        drawStatic(simGC);

        for (Customer c : customers) {

            if (c.isVIP()) {
                simGC.setFill(Color.BLUE);   // VIP
            } else {
                simGC.setFill(Color.RED);    // GA
            }

            simGC.fillOval(c.getX(), c.getY(), 8, 8);
            simGC.setFill(Color.BLACK);
            simGC.setFont(new Font(16));
            simGC.fillText("Salissa: " + hallCount, 1100, 30);
            simGC.fillText("Voit säätää simulaation nopeutta nuolinäppäimillä", 1000, 700);
            simGC.setFill(Color.BLACK);
            simGC.fillText("Simulaation nopeus: " + Controller.simulationSpeed, 1000, 670);
        }
    }

    private static void drawStatic(GraphicsContext gc) {
        int offsetX = 400;
        int offsetY = 30;
        int boxWidth = 150;
        int boxHeight = 270;
        Canvas simCanvas = new Canvas(1280,720);

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}