package org.lore.mqtt;

import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;

public class MqttPublisher {
    private MqttConfig conf;
    private IMqttClient publisher;
    private MqttConnectOptions connectOpts;

    public MqttPublisher(MqttConfig conf) throws MqttException {
        this.conf = conf;
        connectOpts = new MqttConnectOptions();
        connectOpts.setUserName(conf.getUsername());
        connectOpts.setPassword(conf.getPassword().toCharArray());
        this.publisher = new MqttClient(conf.getURI(),conf.getClientID());
    }
    public void connect() throws MqttException {
        publisher.connect(connectOpts);
    }
    public void publish(String topic,String msg) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(msg.getBytes(StandardCharsets.UTF_8));
        publisher.publish(topic,mqttMessage);
        System.out.println("Message "+msg+" sent to topic "+ topic);

    }
}
