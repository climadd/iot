package com.random.app;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttPublisher;

public class PublisherApp {
    public static void main(String[] args) throws MqttException, InterruptedException {

//        String publisherID="TestPubIDLore";
//        IMqttClient publisher= new MqttClient("tcp://smartcity-challenge.edu-al.unipmn.it",publisherID);
//        MqttConnectOptions connectOpts= new MqttConnectOptions();
//        connectOpts.setUserName("pissir");
//        connectOpts.setPassword("pissir2020".toCharArray());
//
//        publisher.connect(connectOpts);
//        MqttMessage msg =new MqttMessage("prova prova 1".getBytes(StandardCharsets.UTF_8));
//        String topic = "/topic/test/lore";
//        publisher.publish(topic,msg);

        MqttConfig mqttConf = new MqttConfig("TestPubIDLore","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttPublisher mqttPub = new MqttPublisher(mqttConf);
        mqttPub.connect();
        for (int i = 0; i < 4; i++) {
            mqttPub.publish("/topic/test/lore", System.currentTimeMillis() + " - msg");
            Thread.sleep(1000L);

        }
    }
}
