package org.lore.mqtt.backend.simu;

import org.lore.simu.attuatori.SimulatorConfig;

import java.io.IOException;

public class SimulatorGUIApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        SimulatorConfig simulatorConfig = new SimulatorConfig("3","5",5001,5002,6001,6002,7001,7002);
        SimulatorGUI simulatorGUI = new SimulatorGUI(simulatorConfig);
        simulatorGUI.start();

    }
}
