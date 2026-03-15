package controller;

import view.SimGUI;

/**
 * Handle's the launching of the simGUI view.
 */
public class SimController {

    /**
     * Runs the simGUI viewer launch method.
     * @param args Main arguments.
     */
    public static void main(String[] args) {
        SimGUI.launch(SimGUI.class);
    }
}