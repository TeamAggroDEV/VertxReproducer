package dev.teamaggro;

public class Backend {

    private final String protocol, ip;
    private final int port;

    public Backend(final String protocol, final String ip, final int port) {
        this.protocol = protocol;
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }
}
