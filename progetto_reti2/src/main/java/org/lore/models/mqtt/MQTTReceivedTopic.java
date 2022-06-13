package org.lore.models.mqtt;

public class MQTTReceivedTopic {
    private String enterprise;
    private String field;
    private MQTTMessageDevice device;
    private MQTTDeviceType deviceType;
    private MQTTTopicDirection direction;

    public MQTTReceivedTopic() {
    }

    public MQTTReceivedTopic(String enterprise, String field, MQTTMessageDevice device, MQTTDeviceType deviceType, MQTTTopicDirection direction) {
        this.enterprise = enterprise;
        this.field = field;
        this.device = device;
        this.deviceType = deviceType;
        this.direction = direction;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public MQTTMessageDevice getDevice() {
        return device;
    }

    public void setDevice(MQTTMessageDevice device) {
        this.device = device;
    }

    public MQTTDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(MQTTDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public MQTTTopicDirection getDirection() {
        return direction;
    }

    public void setDirection(MQTTTopicDirection direction) {
        this.direction = direction;
    }
}
