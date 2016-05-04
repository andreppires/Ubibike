package pt.ulisboa.tecnico.cmov.ubibike;

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
    }

}
