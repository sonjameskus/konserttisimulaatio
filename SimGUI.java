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
                    System.out.println("Speed: " + Controller.simulationSpeed);
                    break;

                case DOWN:
                    Controller.simulationSpeed += 5;
                    System.out.println("Speed: " + Controller.simulationSpeed);
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
        if (simGC == null) return;
        simGC.setFill(Color.WHITE);
        simGC.fillRect(1050, 320, 200, 100);
        simGC.setFill(Color.BLACK);
        simGC.fillText("Salissa: " + count, 1100, 350);
    }

    public static void updateCustomer(Customer c) {
        if (!customers.contains(c)) {
            customers.add(c);
            System.out.println("Customer added to GUI: " + c.getId());

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
        }
    }

    private static void drawStatic(GraphicsContext gc) {
        int offsetX = 400;
        int offsetY = 30;
        int boxWidth = 150;
        int boxHeight = 270;

        gc.setFill(Color.color(.6, .5, 1));
        gc.fillRect(240, 360, boxWidth, boxHeight);
        gc.fillRect(240, 45, boxWidth, boxHeight);

        gc.setFill(Color.color(1, .7, .4));
        gc.fillRect(565, 360, boxWidth, boxHeight);
        gc.fillRect(565, 45, boxWidth, boxHeight);

        gc.setFill(Color.color(.5, 1, .5));
        gc.fillRect(965, 45, boxWidth, 600);


        gc.setFill(Color.color(.3, .3, .3));
        gc.setFont(new Font(gc.getFont().getStyle(), 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.fillText("General Access -Asiakkaat", 1280/2, 720/2 - boxHeight - offsetY);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText("VIP -Asiakkaat", 1280/2, 720/2 + boxHeight + offsetY);
        gc.setFont(new Font(gc.getFont().getStyle(), 24));
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Turvatarkastus", 1280/2 - offsetX, 720/2);
        gc.fillText("Narikka", 1280/2, 720/2);
        gc.fillText("Oheistuotemyymälä", 1280/2 + offsetX, 720/2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}