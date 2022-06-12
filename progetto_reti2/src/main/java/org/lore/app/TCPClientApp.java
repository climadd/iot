package org.lore.app;

import org.lore.tcp.DummyTCPClient;
import org.lore.tcp.TCPConfig;

import java.io.IOException;

public class TCPClientApp {
    public static void main(String[] args) throws IOException {
        TCPConfig conf = new TCPConfig(9999,"127.0.0.1");
        DummyTCPClient tcpClient = new DummyTCPClient();
        String response = tcpClient.sendMessage("aye aye");
        System.out.println(response);
    }
}
