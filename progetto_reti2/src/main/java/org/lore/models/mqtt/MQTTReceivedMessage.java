package org.lore.models.mqtt;

public class MQTTReceivedMessage {
   private MQTTMessageType type;
   private MQTTMessageDevice device;
   private MQTTMessageState state;
   private MQTTMessageLevel level;

    public MQTTReceivedMessage() {
    }

    public MQTTMessageType getType() {
        return type;
    }

    public void setType(MQTTMessageType type) {
        this.type = type;
    }

    public MQTTMessageDevice getDevice() {
        return device;
    }

    public void setDevice(MQTTMessageDevice device) {
        this.device = device;
    }

    public MQTTMessageState getState() {
        return state;
    }

    public void setState(MQTTMessageState state) {
        this.state = state;
    }

    public MQTTMessageLevel getLevel() {
        return level;
    }

    public void setLevel(MQTTMessageLevel level) {
        this.level = level;
    }

    public MQTTReceivedMessage(MQTTMessageType type, MQTTMessageDevice device, MQTTMessageState state, MQTTMessageLevel level) {
        this.type = type;
        this.device = device;
        this.state = state;
        this.level = level;
    }
}