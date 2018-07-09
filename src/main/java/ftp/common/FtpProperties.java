package ftp.common;

import java.util.Map;

public class FtpProperties {

    private String server;

    private int port;

    private String userName;

    private String password;

    private Map<String, String> properties;

    public FtpProperties(String server, int port, String userName, String password) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    int getPort() {
        return port;
    }

    void setPort(int port) {
        this.port = port;
    }

    String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    FtpProperties(Map<String, Object> properties) {
        this.server = properties.get("server").toString();
        this.port = Integer.valueOf(properties.get("port").toString());
        this.userName = properties.get("userName").toString();
        this.password = properties.get("password").toString();
    }
}
