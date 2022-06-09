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
    private Socket clientSocket;
    protected PrintWriter out;
    private BufferedReader in;

    public TCPServer(TCPConfig tcpConfig){
        this.conf=tcpConfig;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(conf.getPort());
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line;
        while((line = in.readLine())!= null){
            compute(line);
        }
    }

    public abstract void compute(String line);

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}

