package pt.ulisboa.tecnico.cmov.ubibike.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;

/**
 * Created by wefbak on 04/05/2016.
 */
public class Peers implements Serializable {

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getVirtualIP() {
        return virtualIP;
    }

    public void setVirtualIP(String virtualIP) {
        this.virtualIP = virtualIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SimWifiP2pSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(SimWifiP2pSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ObjectOutputStream getSocketOutStream() {
        return socketOutStream;
    }

    public void setSocketOutStream(ObjectOutputStream socketOutStream) {
        this.socketOutStream = socketOutStream;
    }

    private String deviceName;
    private String virtualIP;
    private int port;
    private SimWifiP2pSocket clientSocket = null;
    private ObjectOutputStream socketOutStream;
    private ObjectInputStream socketInputStream;
    private boolean owner;

    public Peers(String deviceName, String virtualIP, int port) {
        this.deviceName = deviceName;
        this.virtualIP = virtualIP;
        this.port = port;
    }

    public Peers(String deviceName, String virtualIP) {
        this.deviceName = deviceName;
        this.virtualIP = virtualIP;
    }

    @Override
    public String toString() {
        return "[" + this.deviceName + ":" + this.virtualIP + "]";
    }
}
