package org.lore.simu;

import org.lore.simu.attuatori.SimulatorConfig;
import org.lore.tcp.TCPConfig;
import org.lore.tcp.TempTCPServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulatorGUI {

    private TempTCPServer tempTCPSensorServer;
    private TempTCPServer tempTCPActuatorServer;
    private JFrame window;
    private JPanel tempPanel;
    private  JLabel title;
    private JSeparator titleSeparator;
    private JSeparator tempSeparator;

    private JLabel tempLed;
    private JLabel tempLevel;
    private JLabel tempMode;
    private ExecutorService pool;
    private JLabel tempValue;

    public SimulatorGUI(SimulatorConfig simulatorConfig) throws IOException {

        pool = Executors.newFixedThreadPool(6);

        TCPConfig tempTCPSensorConfig = new TCPConfig(simulatorConfig.getTempTCPServerSensorPort());
        tempTCPSensorServer = new TempTCPServer(tempTCPSensorConfig);

        TCPConfig tempTCPActuatorConfig = new TCPConfig(simulatorConfig.getTempTCPServerActuatorPort());
        tempTCPActuatorServer = new TempTCPServer(tempTCPActuatorConfig);

        window = new JFrame();
        window.setSize(600,400);
        tempPanel = new JPanel();
        tempPanel.setSize(400,100);
        BoxLayout tempLayout = new BoxLayout(tempPanel,BoxLayout.PAGE_AXIS);
        tempPanel.setLayout(tempLayout);
        title = new JLabel("CURRENT SIMULATOR: "+ simulatorConfig.getEnterprise()+"/"+simulatorConfig.getField() );
        titleSeparator = new JSeparator();
        titleSeparator.setOrientation(SwingConstants.HORIZONTAL);
        titleSeparator.setForeground(Color.RED);
        tempSeparator = new JSeparator();
        tempSeparator.setOrientation(SwingConstants.HORIZONTAL);
        tempSeparator.setForeground(Color.ORANGE);
        tempLed = new JLabel("ACTUATOR ⬤");
        tempLevel = new JLabel("TEMPERATURE LEVEL: "+ tempTCPActuatorServer.getLevel());
        tempMode = new JLabel("TEMPERATURE MODE: "+ tempTCPActuatorServer.getMode());
        tempValue = new JLabel("SENSOR\nTEMPERATURE VALUE: "+ tempTCPSensorServer.getValue());


    }

    public void start() throws IOException, InterruptedException {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    tempTCPActuatorServer.start();
                    System.out.println("Started TempActuatorServer");
                } catch (IOException e) {
                    System.out.println("Exception Temp Actuator Server "+ e.getMessage());
                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                    e.printStackTrace();
                }

            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    tempTCPSensorServer.startTempGenerator();
                    System.out.println("Started TempSensorServer");
                    tempTCPSensorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Temp Sensor Server "+ e.getMessage());
                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                    e.printStackTrace();
                }

            }
        });

        switch(tempTCPActuatorServer.getState()){
            case on:
                tempLed.setForeground(Color.GREEN);
                break;
            case off:
                tempLed.setForeground(Color.RED);
                break;
        }
        window.add(title,BorderLayout.NORTH);

        tempPanel.add(titleSeparator);
        tempPanel.add(tempValue);

        tempPanel.add(tempLed);
        tempPanel.add(tempLevel);
        tempPanel.add(tempMode);
        tempPanel.add(tempSeparator);

        window.add(tempPanel);
        window.setVisible(true);
        while(true){
            Thread.sleep(2000L);
            refreshGUI();
        }

    }

    private void refreshGUI(){
        switch(tempTCPActuatorServer.getState()) {
            case on:
                tempLed.setForeground(Color.GREEN);
                break;
            case off:
                tempLed.setForeground(Color.RED);
                break;
        }
        tempLevel.setText("TEMPERATURE LEVEL: "+ tempTCPActuatorServer.getLevel());
        tempMode.setText("TEMPERATURE MODE: "+ tempTCPActuatorServer.getMode());
        tempValue.setText("TEMPERATURE VALUE: "+ tempTCPSensorServer.getValue());

    }

}
