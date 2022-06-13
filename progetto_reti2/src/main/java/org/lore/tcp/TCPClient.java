package org.lore.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    private final TCPConfig conf;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public TCPClient(TCPConfig tcpConfig){
        this.conf=tcpConfig;
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(conf.getIp(), conf.getPort());
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
