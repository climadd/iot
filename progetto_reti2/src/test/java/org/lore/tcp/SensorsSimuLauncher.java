package org.lore.tcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SensorsSimuLauncher {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        //Thread Sensore Temperatura
        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig conf = new TCPConfig(5001);
                TempTCPServer sensorServer = new TempTCPServer(conf);
                try {                                                       //prova ad eseguire l'istruzione, se catcha l'eccezione entra nel codice successivo
                    sensorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Temp Sensor Server "+ e.getMessage());
                    e.printStackTrace();                                    //print di tutte le classi che hanno chiamato il metodo per vedere dove si è entrati nell'eccezione
                }
            }
        });

        //Thread Attuatore temperatura
        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig config = new TCPConfig(5002);
                TempTCPServer actuatorServer = new TempTCPServer(config);
                try {
                    actuatorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Temp Actuator Server "+ e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        //Thread sensore Umidita
        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig config = new TCPConfig(6001);
                UmiTCPServer sensorServer = new UmiTCPServer(config);
                try {
                    sensorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Umi Sensor Server "+ e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        //Thread attuatore Umidita
        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig config = new TCPConfig(6002);
                UmiTCPServer actuatorServer = new UmiTCPServer(config);
                try {
                    actuatorServer.start();
                } catch (IOException e) {
                    System.out.println("Exception Umi Actuator Server "+ e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}

