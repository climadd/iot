package org.lore.app;

import org.lore.tcp.DummyTCPClient;
import org.lore.tcp.TCPConfig;

import java.io.IOException;

public class TCPClientApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        TCPConfig conf = new TCPConfig(9999,"127.0.0.1");
        DummyTCPClient tcpClient = new DummyTCPClient(conf);
        tcpClient.startConnection();
        String response = tcpClient.sendMessage("aye aye");
        System.out.println(response);
        Thread.sleep(2000);
        response = tcpClient.sendMessage("ayyyeeeeee");
        System.out.println(response);
        response = tcpClient.sendMessage("ByeBye");
        System.out.println(response);
        response = tcpClient.sendMessage("NotWork"); //errore
        System.out.println(response);

        tcpClient.stopConnection();
    }
}
