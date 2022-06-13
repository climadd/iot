package org.lore.tcp;

import java.io.PrintWriter;

public class DummyTCPServer extends TCPServer {
    public DummyTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
    }

    @Override
    public void compute(String line, PrintWriter out) {
        System.out.println("Dummy TCP Server receives: "+ line);
        out.println("I received this message: "+ line.toUpperCase()); //operazione sul contenuto della stringa
    }

    @Override
    public boolean terminate(String line, PrintWriter out) {
        if(line.equals("ByeBye")){
            out.println("Connection with Client closed. Bye");
            return true;
        }
        return false;
    }
}
