package org.lore.app;

import org.lore.tcp.GreetClient;

import java.io.IOException;

public class TCPClientApp {
    public static void main(String[] args) throws IOException {
        GreetClient tcpClient = new GreetClient();
        tcpClient.startConnection("127.0.0.1",9999);
        String response = tcpClient.sendMessage("aye aye");
        System.out.println(response);
    }
}
