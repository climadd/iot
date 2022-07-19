package org.lore.models.mqtt;

public class MQTTResponseMessage extends MQTTBaseMessage{
    private Float value;
    private Boolean result;         //oggetto con valore null di default, rispetto a false sul primitivo

    public MQTTResponseMessage() {
    }

    public MQTTResponseMessage(MQTTMessageType type, MQTTMessageDevice device, MQTTMessageState state, MQTTMessageLevel level, MQTTMessageMode mode, float value, boolean result) {
        super(type, device, state, level, mode);
        this.value = value;
        this.result = result;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
