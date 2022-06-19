package org.lore.app;


import org.eclipse.paho.client.mqttv3.MqttException;
import org.lore.exception.MQTTConfigException;
import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttReceiver;
import org.lore.mqtt.listeners.DummyListener;
import org.lore.mqtt.listeners.GWBEListener;
import org.lore.tcp.TCPConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReceiverApp {
    public static void main(String[] args) throws MqttException, InterruptedException, MQTTConfigException, IOException {
//        String receiverID = "TestRecIDLore";
//        IMqttClient receiver = new MqttClient("tcp://smartcity-challenge.edu-al.unipmn.it", receiverID);
//        MqttConnectOptions connectOpts = new MqttConnectOptions();
//        connectOpts.setUserName("pissir");
//        connectOpts.setPassword("pissir2020".toCharArray());
//
//        receiver.connect(connectOpts);
//        CountDownLatch timer = new CountDownLatch(2);
//        receiver.subscribe("/topic/test/lore",(topic,msg)->{
//            System.out.println("my message: " + msg);
//            timer.countDown();
//        });
//        timer.await();

        MqttConfig mqttconf = new MqttConfig("TestRecIDLore","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttReceiver mqttRec = new MqttReceiver(mqttconf);
        mqttRec.connect();
        Map<String, TCPConfig> sensorTCPConfigMap = new HashMap<>();
        Map<String, TCPConfig> actuatorTCPConfigMap = new HashMap<>();

        TCPConfig tempTCPConfig = new TCPConfig(5001,"127.0.0.1");
        TCPConfig actTempTCPConfig = new TCPConfig(5002,"127.0.0.1");
        sensorTCPConfigMap.put("temperatura",tempTCPConfig);
        actuatorTCPConfigMap.put("temperatura",actTempTCPConfig);

        TCPConfig umiTCPConfig = new TCPConfig(5003,"127.0.0.1");
        TCPConfig actUmiTCPConfig = new TCPConfig(5003,"127.0.0.1");
        sensorTCPConfigMap.put("umidita",umiTCPConfig);
        actuatorTCPConfigMap.put("umididta",actUmiTCPConfig);

        GWBEListener listener = new GWBEListener(sensorTCPConfigMap, actuatorTCPConfigMap);
        mqttRec.subscribe("azienda3/serra5/+/+/rx", listener);
 //TODO: come per temperatura, faccio i simulatori per le altre 2 mappe illuminazione e umidita

    }
}
