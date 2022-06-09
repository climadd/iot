package com.random.app;

import org.eclipse.paho.client.mqttv3.MqttException;

import org.lore.mqtt.MqttConfig;
import org.lore.mqtt.MqttReceiver;
import org.lore.mqtt.listeners.DummyListener;

public class ReceiverApp {
    public static void main(String[] args) throws MqttException, InterruptedException {
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


        MqttConfig mqttconf = new MqttConfig("TestPubIDLore","pissir","pissir2020","tcp://smartcity-challenge.edu-al.unipmn.it");
        MqttReceiver mqttRec = new MqttReceiver(mqttconf);
        mqttRec.connect();
        DummyListener listener = new DummyListener();
        mqttRec.subscribe("/topic/test/lore", listener);

    }
}
