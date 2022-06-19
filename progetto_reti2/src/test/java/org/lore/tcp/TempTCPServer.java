package org.lore.tcp;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.lore.models.mqtt.MQTTReceivedMessage;

import java.io.IOException;
import java.io.PrintWriter;

public class TempTCPServer extends TCPServer {

    private Gson gson;

    public TempTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
        gson = new Gson();

    }

    @Override
    public void compute(String line, PrintWriter out) {
        System.out.println("TempTCPServer has received: "+line);

        MQTTReceivedMessage msg = gson.fromJson(line, MQTTReceivedMessage.class);       //creo oggetto msg con il contenuto in arrivo da line
        switch(msg.getType()){
            case write:
                //cambio valori dei miei emulatori
                out.println("Set state to "+ msg.getState()+ ". Set level to "+ msg.getLevel()+ ". Set mode to "+ msg.getMode()+".");
                break;
            case read:
                out.println(10);
                break;
        }
    }

    @Override
    public boolean terminate(String line, PrintWriter out) {
        return false;
    }
}
