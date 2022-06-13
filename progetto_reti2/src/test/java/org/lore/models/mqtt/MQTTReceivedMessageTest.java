package org.lore.models.mqtt;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MQTTReceivedMessageTest {

    @Test
    public void jsonTest(){
        String msg = "{\"type\":\"write\",\"device\":\"actuator\",\"state\":\"off\",\"level\":\"high\"}";
        Gson gson = new Gson();
        MQTTReceivedMessage receivedMessage = gson.fromJson(msg,MQTTReceivedMessage.class);
        System.out.println(receivedMessage.getType());
        System.out.println(receivedMessage.getDevice());
        System.out.println(receivedMessage.getState());
        System.out.println(receivedMessage.getLevel());
        Assertions.assertEquals(MQTTMessageType.write, receivedMessage.getType());  //manda eccezione se i parametri sono differenti
    }

}
