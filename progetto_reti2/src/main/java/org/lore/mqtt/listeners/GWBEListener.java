package org.lore.mqtt.listeners;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lore.models.mqtt.*;

/*
    Questa classe riceverà messaggi MQTT da parte del Backend, e se necessario invierà messaggi ai sensori tramite TCP Socket
 */
public class GWBEListener implements IMqttMessageListener {

        private final Gson gson;
    /*
        TOPICS:
            - azienda{idAzienda}/serra{idSerra}/sensori/temperatura/rx TOPIC di ricezione richiesta temperatura da parte del backend
              azienda3/serra5/sensori/temperatura/rx

            - azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/rx TOPIC di ricezione richiesta di cambio stato o richiesta stato attuale
              azienda3/serra5/attuatori/illuminazione/rx

         MESSAGE FORMAT:
            - Messaggio su TOPIC Sensori di richiesta temperatura
               {
                type:"read",
                device:"sensor"
               }

            - Messaggio su TOPIC Attuatori di richiesta cambio stato
               {
                type:"write",
                device:"actuator",
                state:"on/off",
                level:"low/medium/high"
                mode:"auto/manual"
                }

            - Messaggio su TOPIC Attuatori di richiesta stato e livello attuale
               {
                type:"read",
                device:"actuator"
               }
     */

    public GWBEListener(){
        this.gson = new Gson();
    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String[] splittedTopic = topic.split("/");
        if(splittedTopic.length==5) {
            MQTTReceivedTopic mqttReceivedTopic = new MQTTReceivedTopic(splittedTopic[0], splittedTopic[1], MQTTMessageDevice.valueOf(splittedTopic[2]), MQTTDeviceType.valueOf(splittedTopic[3]), MQTTTopicDirection.valueOf(splittedTopic[4]));
//                                              mqttReceivedTopic.setEnterprise(splittedTopic[0]); alternativa
            String msgPayloadJson = new String(message.getPayload());       //trasforma bytes in una stringa
            MQTTReceivedMessage mqttReceivedMessage = gson.fromJson(msgPayloadJson, MQTTReceivedMessage.class);
            System.out.println("Correct Length ");
            System.out.println("Received Topic "+ gson.toJson(mqttReceivedTopic));
            System.out.println("Received Message "+ gson.toJson(mqttReceivedMessage));
        } else {
            System.out.println("Received Topic "+ topic + " has not valid format.");
        }
    }

}
