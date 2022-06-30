package org.lore.mqtt.backend;

import org.eclipse.paho.client.mqttv3.*;
import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttPublisher;


/**
 * Backend simulator for the publish of MQTT messages
 */
public class BackendMessageSender {
    public static void main(String[] args) throws MqttException, InterruptedException {

        MqttConfig mqttConf = new MqttConfig("TestPubIDLore","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttPublisher mqttPub = new MqttPublisher(mqttConf);
        mqttPub.connect();

        mqttPub.publish("3/5/sensori/temperatura/rx", "{\"type\":\"read\",\"device\":\"sensori\"}",true);
        Thread.sleep(1000L);
        mqttPub.publish("3/5/attuatori/temperatura/rx", "{\"type\":\"write\",\"device\":\"attuatori\",\"state\":\"on\",\"level\":\"high\",\"mode\":\"manual\"}",true);
        Thread.sleep(1000L);
        mqttPub.publish("3/5/sensori/umidita/rx","{\"type\":\"read\",\"device\":\"sensori\"}",true);
        Thread.sleep(1000L);
        mqttPub.publish("3/5/attuatori/umidita/rx","{\"type\":\"write\",\"device\":\"attuatori\",\"state\":\"on\",\"level\":\"low\",\"mode\":\"auto\"}",true);
        Thread.sleep(1000L);
        mqttPub.publish("3/5/attuatori/illuminazione/rx","{\"type\":\"read\",\"device\":\"sensori\"}",true);
        Thread.sleep(1000L);
        mqttPub.publish("3/5/attuatori/illuminazione/rx","{\"type\":\"write\",\"device\":\"attuatori\",\"state\":\"on\",\"level\":\"medium\",\"mode\":\"auto\"}",true);

        mqttPub.disconnect(0L);
        mqttPub.close();
    }
}
