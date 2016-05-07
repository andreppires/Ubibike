package pt.ulisboa.tecnico.cmov.ubibike;

import java.util.ArrayList;

/**
 * Created by andreppires on 27-04-2016.
 */
public final class Client {
    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Client.client = client;
    }

    private static Client client;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;


    }

    String username=null;

    protected ArrayList<String> friends = null;

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addFriends(String friendUsername) {
        this.friends.add(friendUsername);
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    int pontos=0;

    public Client(String u, int p){
        this.username=u;
        this.pontos=p;
        this.friends=new ArrayList<String>();
    }

}
