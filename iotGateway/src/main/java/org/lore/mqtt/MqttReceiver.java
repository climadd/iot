package org.lore.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class MqttReceiver {


    private MqttConfig conf;
    private IMqttClient receiver;
    private MqttConnectOptions connectOpts;

    public MqttReceiver(MqttConfig conf) throws MqttException {          //costruttore
        this.conf = conf;
        connectOpts = new MqttConnectOptions();
        connectOpts.setUserName(conf.getUsername());
        connectOpts.setPassword(conf.getPassword().toCharArray());
        this.receiver = new MqttClient(conf.getURI(), conf.getClientID());
    }

    public void connect() throws MqttException {
        receiver.connect(connectOpts);
    }

    public void subscribe(String topic, IMqttMessageListener messageListener) throws MqttException {
        receiver.subscribe(topic, messageListener);
    }
}

