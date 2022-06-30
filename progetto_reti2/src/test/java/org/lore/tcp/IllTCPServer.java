package org.lore.tcp;

import com.google.gson.Gson;
import org.lore.models.mqtt.MQTTReceivedMessage;
import org.lore.models.mqtt.MQTTResponseMessage;

import java.io.PrintWriter;

public class IllTCPServer extends TCPServer{

    private Gson gson;
    public IllTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
        gson = new Gson();
    }

    @Override
    public void compute(String line, PrintWriter out) {
        System.out.println("--> IllTCPServer has received: "+line);

        //i'm gonna build the object msg with the contents that i received through line
        MQTTReceivedMessage msg = gson.fromJson(line, MQTTReceivedMessage.class);
        switch(msg.getType()){
            case write:
                //in case the msg is a write, i'm gonna change the device's state and put together a response message as feedback
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
                System.out.println("<-- IllTCPServer has sent: "+writeJsonMessage);
                break;
            //in case the msg is a read, i'm gonna get the device measure and report it back through a response message as reply
            case read:
                MQTTResponseMessage readResponse = new MQTTResponseMessage();
                readResponse.setDevice(msg.getDevice());
                readResponse.setType(msg.getType());
                readResponse.setValue(12);                      //sto rispondendo alla read
                String readJsonMessage = gson.toJson(readResponse);
                out.println(readJsonMessage);
                System.out.println("<-- IllTCPServer has sent: "+ readJsonMessage);
                break;
        }
    }

    @Override
    public boolean terminate(String line, PrintWriter out) {
        return false;
    }
}


