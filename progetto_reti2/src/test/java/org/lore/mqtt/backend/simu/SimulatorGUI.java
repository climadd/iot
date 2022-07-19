package org.lore.mqtt.backend.simu;

import org.lore.simu.attuatori.SimulatorConfig;
import org.lore.tcp.IllTCPServer;
import org.lore.tcp.TCPConfig;
import org.lore.tcp.TempTCPServer;
import org.lore.tcp.UmiTCPServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulatorGUI {

    private TempTCPServer tempTCPSensorServer;
    private TempTCPServer tempTCPActuatorServer;
    private UmiTCPServer umiTCPSensorServer;
    private UmiTCPServer umiTCPActuatorServer;
    private IllTCPServer illTCPSensorServer;
    private IllTCPServer illTCPActuatorServer;

    private JFrame window;
    private JPanel tempPanel;
    private JPanel umiPanel;
    private JPanel illPanel;
    private  JLabel title;
    private JSeparator titleSeparator;
    private JSeparator tempSeparator;
    private JSeparator umiSeparator;
    private JSeparator illSeparator; //inutile?

    private JLabel tempLed;
    private JLabel tempLevel;
    private JLabel tempMode;
    private JLabel tempValue;
    private JLabel umiLed;
    private JLabel umiLevel;
    private JLabel umiMode;
    private JLabel umiValue;
    private JLabel illLed;
    private JLabel illLevel;
    private JLabel illMode;
    private JLabel illValue;
    private ExecutorService pool;

    public SimulatorGUI(SimulatorConfig simulatorConfig) throws IOException {

        pool = Executors.newFixedThreadPool(6);


        TCPConfig tempTCPSensorConfig = new TCPConfig(simulatorConfig.getTempTCPServerSensorPort());
        tempTCPSensorServer = new TempTCPServer(tempTCPSensorConfig);
        TCPConfig tempTCPActuatorConfig = new TCPConfig(simulatorConfig.getTempTCPServerActuatorPort());
        tempTCPActuatorServer = new TempTCPServer(tempTCPActuatorConfig);

        TCPConfig umiTCPSensorConfig = new TCPConfig(simulatorConfig.getUmiTCPServerSensorPort());
        umiTCPSensorServer = new UmiTCPServer(umiTCPSensorConfig);
        TCPConfig umiTCPActuatorConfig = new TCPConfig(simulatorConfig.getUmiTCPServerActuatorPort());
        umiTCPActuatorServer = new UmiTCPServer(umiTCPActuatorConfig);

        TCPConfig illTCPSensorConfig = new TCPConfig(simulatorConfig.getIllTCPServerSensorPort());
        illTCPSensorServer = new IllTCPServer(illTCPSensorConfig);
        TCPConfig illTCPActuatorConfig = new TCPConfig(simulatorConfig.getIllTCPServerActuatorPort());
        illTCPActuatorServer = new IllTCPServer(illTCPActuatorConfig);

        //GUI Components
        window = new JFrame();
        window.setSize(600,450);

        tempPanel = new JPanel();
        tempPanel.setSize(600,400);

//
//        umiPanel = new JPanel();
//        umiPanel.setSize(600,100);
//
//        illPanel = new JPanel();
//        illPanel.setSize(600,100);


        title = new JLabel("CURRENT SIMULATOR: "+ simulatorConfig.getEnterprise()+"/"+simulatorConfig.getField() );
       // titleSeparator = new JSeparator();
//        titleSeparator.setOrientation(SwingConstants.HORIZONTAL);
//        titleSeparator.setForeground(Color.RED);

        //campi temperatura
        tempSeparator = new JSeparator();
//        tempSeparator.setOrientation(SwingConstants.HORIZONTAL);
//        tempSeparator.setForeground(Color.ORANGE);
        tempLed = new JLabel("ACTUATOR ⬤");
        tempLevel = new JLabel("TEMPERATURE LEVEL: "+ tempTCPActuatorServer.getLevel());
        tempMode = new JLabel("TEMPERATURE MODE: "+ tempTCPActuatorServer.getMode());
        tempValue = new JLabel("SENSOR TEMPERATURE VALUE: "+ tempTCPSensorServer.getValue()+"°C");

        //campi umidità
        umiSeparator = new JSeparator();
//        umiSeparator.setOrientation(SwingConstants.HORIZONTAL);
//        tempSeparator.setForeground(Color.YELLOW);
        umiLed = new JLabel("ACTUATOR ⬤");
        umiLevel = new JLabel("HUMIDITY LEVEL: "+ umiTCPActuatorServer.getLevel());
        umiMode = new JLabel("HUMIDITY MODE: "+ umiTCPActuatorServer.getMode());
        umiValue = new JLabel("SENSOR HUMIDITY VALUE: "+ umiTCPSensorServer.getValue()+"%");

        //campi illuminazione
//        illSeparator = new JSeparator();
//        illSeparator.setOrientation(SwingConstants.HORIZONTAL);
//        illSeparator.setForeground(Color.GREEN);
        illLed = new JLabel("ACTUATOR ⬤");
        illLevel = new JLabel("ILLUMAINATION LEVEL: "+ illTCPActuatorServer.getLevel());
        illMode = new JLabel("ILLUMINATION MODE: "+ illTCPActuatorServer.getMode());
        illValue = new JLabel("SENSOR ILLUMINATION VALUE: "+ illTCPSensorServer.getValue()+"lm");
    }

    public void start() throws IOException, InterruptedException {
        //Threads per la connessione TCP di devices TEMPERATURA
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
        //Threads per la connessione TCP dei devices UMIDITA
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    umiTCPActuatorServer.start();
                    System.out.println("Started UmiActuatorServer");
                } catch (IOException e) {
                    System.out.println("Exception Umi Actuator Server "+ e.getMessage());
                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                    e.printStackTrace();
                }

            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    umiTCPSensorServer.startUmiGenerator();
                    System.out.println("Started UmiSensorServer");
                    umiTCPSensorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Umi Sensor Server "+ e.getMessage());
                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                    e.printStackTrace();
                }

            }
        });
        //Threads per la connessione TCP dei devices ILLUMINAZIONE
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    illTCPActuatorServer.start();
                    System.out.println("Started IllActuatorServer");
                } catch (IOException e) {
                    System.out.println("Exception Ill Actuator Server "+ e.getMessage());
                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                    e.printStackTrace();
                }

            }
        });
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    illTCPSensorServer.startIllGenerator();
                    System.out.println("Started IllSensorServer");
                    illTCPSensorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Ill Sensor Server "+ e.getMessage());
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
        switch(umiTCPActuatorServer.getState()){
            case on:
                umiLed.setForeground(Color.GREEN);
                break;
            case off:
                umiLed.setForeground(Color.RED);
                break;
        }
        switch(illTCPActuatorServer.getState()){
            case on:
                illLed.setForeground(Color.GREEN);
                break;
            case off:
                illLed.setForeground(Color.RED);
                break;
        }
        window.add(title,BorderLayout.NORTH);

//       tempPanel.add(titleSeparator);
        tempPanel.add(tempValue);
        tempPanel.add(tempLed);
        tempPanel.add(tempLevel);
        tempPanel.add(tempMode);

        tempPanel.add(tempSeparator);
        tempPanel.add(umiValue);
        tempPanel.add(umiLed);
        tempPanel.add(umiLevel);
        tempPanel.add(umiMode);


        tempPanel.add(umiSeparator);
        tempPanel.add(illValue);
        tempPanel.add(illLed);
        tempPanel.add(illLevel);
        tempPanel.add(illMode);
       // illPanel.add(illSeparator);

        window.add(tempPanel);
//        window.add(umiPanel);
//        window.add(illPanel);
        window.setVisible(true);

        while(true){
            Thread.sleep(2000L);
            refreshGUI();
        }

    }

    //GUI Gets refreshed every 2 seconds to show updates on devices' values
    private void refreshGUI(){
        switch(tempTCPActuatorServer.getState()) {
            case on:
                tempLed.setForeground(Color.GREEN);
                break;
            case off:
                tempLed.setForeground(Color.RED);
                break;
        }
        switch(umiTCPActuatorServer.getState()){
            case on:
                umiLed.setForeground(Color.GREEN);
                break;
            case off:
                umiLed.setForeground(Color.RED);
                break;
        }
        switch(illTCPActuatorServer.getState()){
            case on:
                illLed.setForeground(Color.GREEN);
                break;
            case off:
                illLed.setForeground(Color.RED);
                break;
        }

        tempLevel.setText("TEMPERATURE LEVEL: "+ tempTCPActuatorServer.getLevel());
        tempMode.setText("TEMPERATURE MODE: "+ tempTCPActuatorServer.getMode());
        tempValue.setText("SENSOR TEMPERATURE VALUE: "+ tempTCPSensorServer.getValue()+"°C");

        umiLevel.setText("TEMPERATURE LEVEL: "+ umiTCPActuatorServer.getLevel());
        umiMode.setText("TEMPERATURE MODE: "+ umiTCPActuatorServer.getMode());
        umiValue.setText("SENSOR TEMPERATURE VALUE: "+ umiTCPSensorServer.getValue()+"%");

        illLevel.setText("TEMPERATURE LEVEL: "+ illTCPActuatorServer.getLevel());
        illMode.setText("TEMPERATURE MODE: "+ illTCPActuatorServer.getMode());
        illValue.setText("SENSOR TEMPERATURE VALUE: "+ illTCPSensorServer.getValue()+"lm");
    }

}
