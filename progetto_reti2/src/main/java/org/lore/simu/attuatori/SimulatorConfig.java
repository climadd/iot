package org.lore.simu.attuatori;

public class SimulatorConfig {
    private String enterprise;
    private String field;
    private int tempTCPServerSensorPort;
    private int tempTCPServerActuatorPort;
    private int umiTCPServerSensorPort;
    private int umiTCPServerActuatorPort;
    private int illTCPServerSensorPort;
    private int illTCPServerActuatorPort;

    public SimulatorConfig() {
    }

    public SimulatorConfig(String enterprise, String field, int tempTCPServerSensorPort, int tempTCPServerActuatorPort, int umiTCPServerSensorPort, int umiTCPServerActuatorPort, int illTCPServerSensorPort, int illTCPServerActuatorPort) {
        this.enterprise = enterprise;
        this.field = field;
        this.tempTCPServerSensorPort = tempTCPServerSensorPort;
        this.tempTCPServerActuatorPort = tempTCPServerActuatorPort;
        this.umiTCPServerSensorPort = umiTCPServerSensorPort;
        this.umiTCPServerActuatorPort = umiTCPServerActuatorPort;
        this.illTCPServerSensorPort = illTCPServerSensorPort;
        this.illTCPServerActuatorPort = illTCPServerActuatorPort;
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

    public int getUmiTCPServerSensorPort() {
        return umiTCPServerSensorPort;
    }

    public void setUmiTCPServerSensorPort(int umiTCPServerSensorPort) {
        this.umiTCPServerSensorPort = umiTCPServerSensorPort;
    }

    public int getUmiTCPServerActuatorPort() {
        return umiTCPServerActuatorPort;
    }

    public void setUmiTCPServerActuatorPort(int umiTCPServerActuatorPort) {
        this.umiTCPServerActuatorPort = umiTCPServerActuatorPort;
    }

    public int getIllTCPServerSensorPort() {
        return illTCPServerSensorPort;
    }

    public void setIllTCPServerSensorPort(int illTCPServerSensorPort) {
        this.illTCPServerSensorPort = illTCPServerSensorPort;
    }

    public int getIllTCPServerActuatorPort() {
        return illTCPServerActuatorPort;
    }

    public void setIllTCPServerActuatorPort(int illTCPServerActuatorPort) {
        this.illTCPServerActuatorPort = illTCPServerActuatorPort;
    }
}
