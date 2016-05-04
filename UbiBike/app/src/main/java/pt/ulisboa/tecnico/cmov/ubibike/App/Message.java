package pt.ulisboa.tecnico.cmov.ubibike.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wefbak on 04/05/2016.
 */
public class Message implements Serializable {

    private String message;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    private String client;

    public ArrayList<Peers> getConnectedPeersList() {
        return connectedPeersList;
    }

    public void setConnectedPeersList(ArrayList<Peers> connectedPeersList) {
        this.connectedPeersList = connectedPeersList;
    }

    private ArrayList<Peers> connectedPeersList = new ArrayList<Peers>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ArrayList<Peers> getArrayPeers() {
        return arrayPeers;
    }

    public void setArrayPeers(ArrayList<Peers> arrayPeers) {
        this.arrayPeers = arrayPeers;
    }

    private ArrayList<Peers> arrayPeers;

    public Message(String message){
        this.message = message;
    }
}
