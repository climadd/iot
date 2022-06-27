package org.lore.tcp;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.lore.models.mqtt.MQTTReceivedMessage;
import org.lore.models.mqtt.MQTTResponseMessage;

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
        System.out.println("--> TempTCPServer has received: "+line);

        MQTTReceivedMessage msg = gson.fromJson(line, MQTTReceivedMessage.class);       //creo oggetto msg con il contenuto in arrivo da line
        switch(msg.getType()){
            case write:
                //TODO: cambio valori dei miei emulatori
                MQTTResponseMessage writeResponse = new MQTTResponseMessage();
                writeResponse.setDevice(msg.getDevice());
                writeResponse.setLevel(msg.getLevel());
                writeResponse.setMode(msg.getMode());
                writeResponse.setState(msg.getState());
                writeResponse.setType(msg.getType());
                writeResponse.setResult(true);          //sto rispondendo ad una write
                String writeJsonMessage = gson.toJson(writeResponse);
                out.println(writeJsonMessage);
                System.out.println("<-- TempTCPServer has sent: "+writeJsonMessage);
                break;
            case read:
                MQTTResponseMessage readResponse = new MQTTResponseMessage();
                readResponse.setDevice(msg.getDevice());
                readResponse.setType(msg.getType());
                readResponse.setValue(12);                      //sto rispondendo alla read
                String readJsonMessage = gson.toJson(readResponse);
                out.println(readJsonMessage);
                System.out.println("<-- TempTCPServer has sent: "+ readJsonMessage);
                break;
        }
    }

    @Override
    public boolean terminate(String line, PrintWriter out) {
        return false;
    }
}
