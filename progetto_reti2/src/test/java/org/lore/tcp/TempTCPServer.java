package org.lore.tcp;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.lore.models.mqtt.*;
import org.lore.utils.RandomUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TempTCPServer extends TCPServer {

    private Gson gson;

    //actuator's values
    private Float value;
    private MQTTMessageState state;
    private MQTTMessageLevel level;
    private MQTTMessageMode mode;
    private ScheduledExecutorService pool;


    public TempTCPServer(TCPConfig tcpConfig) {
        super(tcpConfig);
        gson = new Gson();
        value = 0F;
        state = MQTTMessageState.off;
        level = MQTTMessageLevel.medium;
        mode = MQTTMessageMode.auto;
        pool = Executors.newScheduledThreadPool(1);
    }

    public MQTTMessageState getState() {
        return state;
    }

    public MQTTMessageLevel getLevel() {
        return level;
    }

    public MQTTMessageMode getMode() {
        return mode;
    }

    public Float getValue() {
        return value;
    }

    public void startTempGenerator(){
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                value = (float) RandomUtils.getRandomByRange(-10, 50);

            }
        },0L,30L, TimeUnit.SECONDS);

    }

    @Override
    public void compute(String line, PrintWriter out) {
        System.out.println("--> TempTCPServer has received: "+line);

        //i'm gonna build the object msg with the contents that i received through line
        MQTTReceivedMessage msg = gson.fromJson(line, MQTTReceivedMessage.class);
        switch(msg.getType()){
            //in case the msg is a write, i'm gonna change the device's state and put together a response message as feedback
            case write:
                // TODO: cambio valori dei miei emulatori
                MQTTResponseMessage writeResponse = new MQTTResponseMessage();
                writeResponse.setDevice(msg.getDevice());
                writeResponse.setLevel(msg.getLevel());
                writeResponse.setMode(msg.getMode());
                writeResponse.setState(msg.getState());
                writeResponse.setType(msg.getType());
                writeResponse.setResult(true);          //sto rispondendo ad una write
                String writeJsonMessage = gson.toJson(writeResponse);
                state = msg.getState();
                level = msg.getLevel();
                mode = msg.getMode();
                out.println(writeJsonMessage);
                System.out.println("<-- TempTCPServer has sent: "+writeJsonMessage);
                break;
            //in case the msg is a read, i'm gonna get the device measure and report it back through a response message as reply
            case read:
                MQTTResponseMessage readResponse = new MQTTResponseMessage();
                readResponse.setDevice(msg.getDevice());
                readResponse.setType(msg.getType());
                readResponse.setValue(value);                      //sto rispondendo alla read
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
