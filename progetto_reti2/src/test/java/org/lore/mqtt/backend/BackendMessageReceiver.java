package org.lore.mqtt.backend;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttReceiver;
import org.lore.mqtt.listeners.DummyListener;

/**
 * Backend simulator for the received MQTT messages
 */
public class BackendMessageReceiver {
    public static void main(String[] args) throws MqttException {
        MqttConfig mqttConfig = new MqttConfig("BEMessageReceiverID","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttReceiver mqttReceiver = new MqttReceiver(mqttConfig);
        mqttReceiver.connect();
        mqttReceiver.subscribe("3/5/+/+/sx",new DummyListener());
        System.out.println("Backend Mqtt message receiver started.");

    }
}
