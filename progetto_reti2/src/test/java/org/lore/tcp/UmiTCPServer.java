package org.lore.tcp;

import com.google.gson.Gson;
import org.lore.models.mqtt.MQTTDeviceType;
import org.lore.models.mqtt.MQTTReceivedMessage;
import org.lore.models.mqtt.MQTTResponseMessage;

import java.io.PrintWriter;

public class UmiTCPServer extends TCPServer{

    private Gson gson;

    public UmiTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
        gson = new Gson();
    }

    @Override
    public void compute(String line, PrintWriter out) {
        System.out.println("--> UmiTCPServer has received: "+ line);

        MQTTReceivedMessage msg = gson.fromJson(line,MQTTReceivedMessage.class);
        switch(msg.getType()) {
            case write:
                MQTTResponseMessage writeResponse = new MQTTResponseMessage();
                writeResponse.setDevice(msg.getDevice());
                writeResponse.setLevel(msg.getLevel());
                writeResponse.setMode(msg.getMode());
                writeResponse.setState(msg.getState());
                writeResponse.setType(msg.getType());
                writeResponse.setResult(true);                  //sto rispondendo ad una write
                String writeJsonMessage = gson.toJson(writeResponse);
                out.println(writeJsonMessage);
                System.out.println("<-- UmiTCPServer has sent: "+ writeJsonMessage);
                break;
            case read:
                MQTTResponseMessage readResponse = new MQTTResponseMessage();
                readResponse.setDevice(msg.getDevice());
                readResponse.setType(msg.getType());
                readResponse.setValue(62);                      //sto rispondendo alla read
                String readJsonMessage = gson.toJson(readResponse);
                out.println(readJsonMessage);
                System.out.println("<-- UmiTCPServer has sent: "+ readJsonMessage);
                break;
        }
    }

    @Override
    public boolean terminate(String line, PrintWriter out) {
        return false;
    }
}
