package com.hendrychjan;

public class DataCarrierSingleton {
    private static DataCarrierSingleton singleton;

    private int port;
    private String nick;
    private String ip;

    private DataCarrierSingleton() {}

    public static DataCarrierSingleton getInstance() {
        if (singleton == null) {
            singleton = new DataCarrierSingleton();
        }
        return singleton;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return this.nick;
    }

}
