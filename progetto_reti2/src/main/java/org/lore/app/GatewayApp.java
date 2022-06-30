package org.lore.app;


import org.eclipse.paho.client.mqttv3.MqttException;
import org.lore.exception.MQTTConfigException;
import org.lore.models.mqtt.MQTTDeviceType;
import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttReceiver;
import org.lore.mqtt.listeners.GWBEListener;
import org.lore.tcp.TCPConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GatewayApp {
    public static void main(String[] args) throws MqttException, InterruptedException, MQTTConfigException, IOException {

        MqttConfig mqttconf = new MqttConfig("GWReceiverID","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttReceiver mqttRec = new MqttReceiver(mqttconf);
        mqttRec.connect();

        //create a map for sensor devices, another one for actuator devices
        Map<String, TCPConfig> sensorTCPConfigMap = new HashMap<>();
        Map<String, TCPConfig> actuatorTCPConfigMap = new HashMap<>();

        //create two temperatura TCP configurations (sensor and actuator) and feature them onto their respective map
        TCPConfig tempTCPConfig = new TCPConfig(5001,"127.0.0.1");
        TCPConfig actTempTCPConfig = new TCPConfig(5002,"127.0.0.1");
        sensorTCPConfigMap.put(MQTTDeviceType.temperatura.name(), tempTCPConfig);
        actuatorTCPConfigMap.put(MQTTDeviceType.temperatura.name(), actTempTCPConfig);

        //equivalent, but for umidita
        TCPConfig umiTCPConfig = new TCPConfig(6001,"127.0.0.1");
        TCPConfig actUmiTCPConfig = new TCPConfig(6002,"127.0.0.1");
        sensorTCPConfigMap.put(MQTTDeviceType.umidita.name(), umiTCPConfig);
        actuatorTCPConfigMap.put(MQTTDeviceType.umidita.name(), actUmiTCPConfig);

        //equivalent, but for illuminazione
        TCPConfig illTCPConfig = new TCPConfig(7001,"127.0.0.1");
        TCPConfig actIllTCPConfig = new TCPConfig(7002,"127.0.0.1");
        sensorTCPConfigMap.put(MQTTDeviceType.illuminazione.name(), illTCPConfig);
        actuatorTCPConfigMap.put(MQTTDeviceType.illuminazione.name(), actIllTCPConfig);

        //create a new listener through the GWBEListener class and subcribe to one or various topics
        MqttConfig mqttPublisherConfig = new MqttConfig("GWPublisherID","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        GWBEListener listener = new GWBEListener(sensorTCPConfigMap, actuatorTCPConfigMap,mqttPublisherConfig);
        mqttRec.subscribe("3/5/+/+/rx", listener);

    }
}
