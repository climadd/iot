package org.lore.tcp;

public class TCPConfig {

    private int port;
    private String ip;

    //
    public TCPConfig(int port){
        this.port=port;
    }

    public TCPConfig(int port, String ip){
        this.port=port;
        this.ip=ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
