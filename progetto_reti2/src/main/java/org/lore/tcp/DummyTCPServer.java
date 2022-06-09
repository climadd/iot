package org.lore.tcp;

public class DummyTCPServer extends TCPServer {
    public DummyTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
    }

    @Override
    public void compute(String line) {
        System.out.println("Dummy TCP Server receives: "+ line);
        out.println("I received this message: "+ line.toUpperCase()); //operazione sul contenuto della stringa passata attraverso la socket
    }
}
