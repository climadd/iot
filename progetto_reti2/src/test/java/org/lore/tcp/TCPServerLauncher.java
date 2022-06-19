package org.lore.tcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerLauncher {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig conf = new TCPConfig(5001);
                TempTCPServer sensorServer = new TempTCPServer(conf);
                try {
                    sensorServer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pool.submit(new Runnable() {
            @Override
            public void run() {
                TCPConfig config = new TCPConfig(5002);
                TempTCPServer actuatorServer = new TempTCPServer(config);
                try {
                    actuatorServer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}

