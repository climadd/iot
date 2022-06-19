package org.lore.mqtt.listeners;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lore.exception.MQTTConfigException;
import org.lore.models.mqtt.*;
import org.lore.tcp.SimpleTCPClient;
import org.lore.tcp.TCPConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    Questa classe ascolterà i messaggi MQTT da parte del Backend, e se necessario invierà messaggi ai sensori tramite TCP Socket
 */
public class GWBEListener implements IMqttMessageListener {

    /*
        TOPICS:
            - azienda{idAzienda}/serra{idSerra}/sensori/temperatura/rx TOPIC di ricezione richiesta temperatura da parte del backend
              3/5/sensori/temperatura/rx

            - azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/rx TOPIC di ricezione richiesta di cambio stato o richiesta stato attuale
              3/5/attuatori/illuminazione/rx

         MESSAGE FORMAT:
            - Messaggio su TOPIC Sensori di richiesta temperatura
               {
                type:"read",
                device:"sensori"
               }

            - Messaggio su TOPIC Attuatori di richiesta cambio stato
               {
                type:"write",
                device:"attuatori",
                state:"on/off",
                level:"low/medium/high"
                mode:"auto/manual"
                }

            - Messaggio su TOPIC Attuatori di richiesta stato e livello attuale
               {
                type:"read",
                device:"attuatori"
               }
     */

    private final Gson gson;

    private Map<String, SimpleTCPClient> sensorTCPClientMap;      //mappa per creazione dinamica delle connessioni TCP ai miei sensori/attuat.
    private Map<String, SimpleTCPClient> actuatorTCPClientMap;
    public GWBEListener(Map<String, TCPConfig> sensorTCPConfigMap, Map<String, TCPConfig> actuatorTCPConfigMap) throws MQTTConfigException, IOException {
        this.gson = new Gson();
        this.sensorTCPClientMap = new HashMap<>();
        this.actuatorTCPClientMap = new HashMap<>();
        for (String sensorName : sensorTCPConfigMap.keySet()) {               //spiegazione sul test MapTest
            TCPConfig conf = sensorTCPConfigMap.get(sensorName);
            if(conf.getIp()==null){
                throw new MQTTConfigException("TCP Client needs IP not null");
            }
            SimpleTCPClient simpleTCPClient = new SimpleTCPClient(conf);
 //           simpleTCPClient.startConnection();
            sensorTCPClientMap.put(sensorName, simpleTCPClient);
        }
        for(String actuatorName : actuatorTCPConfigMap.keySet()){
            TCPConfig conf = actuatorTCPConfigMap.get(actuatorName);
            if(conf.getIp()==null) {
                throw new MQTTConfigException("TCP Client needs IP not null");
            }
            SimpleTCPClient simpleTCPClient = new SimpleTCPClient(conf);
  //          simpleTCPClient.startConnection();
            actuatorTCPClientMap.put(actuatorName, simpleTCPClient);
        }

    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String[] splittedTopic = topic.split("/");
            System.out.println("listener message arrived: " + new String(message.getPayload()));
            if (splittedTopic.length == 5) {
                MQTTReceivedTopic mqttReceivedTopic = new MQTTReceivedTopic(splittedTopic[0], splittedTopic[1], MQTTMessageDevice.valueOf(splittedTopic[2]), MQTTDeviceType.valueOf(splittedTopic[3]), MQTTTopicDirection.valueOf(splittedTopic[4]));
//                                              mqttReceivedTopic.setEnterprise(splittedTopic[0]); alternativa
                String msgPayloadJson = new String(message.getPayload());       //trasforma bytes in una stringa
                MQTTReceivedMessage mqttReceivedMessage = gson.fromJson(msgPayloadJson, MQTTReceivedMessage.class);
                System.out.println("Received Topic " + gson.toJson(mqttReceivedTopic));
                System.out.println("Received Message " + gson.toJson(mqttReceivedMessage));

                String msg = gson.toJson(mqttReceivedMessage);
                String response;                                //SARA' IL MESSAGGIO CHE INOLTRERO' VERSO IL BACKEND
                switch(mqttReceivedTopic.getDevice()){              //BREAK OBBLIGATORIO ALLA FINE DI OGNI CASE IN SWITCH
                    case sensori:
                        SimpleTCPClient sensorTCPClient = sensorTCPClientMap.get(mqttReceivedTopic.getDeviceType().name());
                        sensorTCPClient.startConnection();
                        response = sensorTCPClient.sendMessage(msg);
                        sensorTCPClient.stopConnection();
                        break;
                    case attuatori:
                        SimpleTCPClient actuatorTCPClient = actuatorTCPClientMap.get(mqttReceivedTopic.getDeviceType().name());
                        actuatorTCPClient.startConnection();
                        response = actuatorTCPClient.sendMessage(msg);
                        actuatorTCPClient.stopConnection();
                        break;
                    default:
                        response = "";
                }
                System.out.println("Response from simulator: "+ response);
            } else {
                System.out.println("Received Topic " + topic + " has not valid format.");
            }
        }catch(Exception ex){
            System.out.println("Exception on message "+ new String(message.getPayload())+ " on topic: "+ topic +" . Error: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

}
