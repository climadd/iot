package org.lore.mqtt;

public class MqttConfig {
    private String clientID;
    private String username;
    private String password;
    private String URI;

    public MqttConfig(String clientID, String username, String password, String uri){
        this.clientID = clientID;
        this.username = username;
        this.password = password;
        this.URI = uri;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
