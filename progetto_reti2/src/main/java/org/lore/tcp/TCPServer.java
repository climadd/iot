package org.lore.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TCPServer {
    private TCPConfig conf;
    private ServerSocket serverSocket;


    public TCPServer(TCPConfig tcpConfig){
        this.conf=tcpConfig;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(conf.getPort());
        while(true){
            new ClientHandler(serverSocket.accept()).start();           //start bloccante, a ogni nuovo ciclo apre una nuova socket
        }
    }

    public abstract void compute(String line, PrintWriter out);

    public abstract boolean terminate(String line, PrintWriter out);

    public void stop() throws IOException {

        serverSocket.close();
    }

    private class ClientHandler extends Thread{
        private Socket clientSocket;
        protected PrintWriter out;
        private BufferedReader in;
        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }
        public void run(){
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if(terminate(line, out))                    //termine della socket corrente, si torna sul while(true) precedente
                        break;
                    compute(line, out);
                }

            }catch(IOException ioException){
                System.out.println("[ERROR] "+ ioException.getMessage());
                ioException.printStackTrace();
            }finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                }catch(IOException ioException){
                    System.out.println("[ERROR] "+ ioException.getMessage());
                    ioException.printStackTrace();
                }
            }
        }
    }

}

