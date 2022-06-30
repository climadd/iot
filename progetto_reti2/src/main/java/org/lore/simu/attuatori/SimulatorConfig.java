package org.lore.simu.attuatori;

public class SimulatorConfig {
    private String enterprise;
    private String field;
    private int tempTCPServerSensorPort;
    private int tempTCPServerActuatorPort;

    public SimulatorConfig() {
    }

    public SimulatorConfig(String enterprise, String field, int tempTCPServerSensorPort, int tempTCPServerActuatorPort) {
        this.enterprise = enterprise;
        this.field = field;
        this.tempTCPServerSensorPort = tempTCPServerSensorPort;
        this.tempTCPServerActuatorPort = tempTCPServerActuatorPort;
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

    public int getTempTCPServerSensorPort() {
        return tempTCPServerSensorPort;
    }

    public void setTempTCPServerSensorPort(int tempTCPServerSensorPort) {
        this.tempTCPServerSensorPort = tempTCPServerSensorPort;
    }

    public int getTempTCPServerActuatorPort() {
        return tempTCPServerActuatorPort;
    }

    public void setTempTCPServerActuatorPort(int tempTCPServerActuatorPort) {
        this.tempTCPServerActuatorPort = tempTCPServerActuatorPort;
    }
}
