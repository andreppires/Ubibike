package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.os.AsyncTask;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.ulisboa.tecnico.cmov.ubibike.Client;
import pt.ulisboa.tecnico.cmov.ubibike.RequestMethod;
import pt.ulisboa.tecnico.cmov.ubibike.RestClient;
import pt.ulisboa.tecnico.cmov.ubibike.Stations;

/**
 * Created by andreppires on 05-05-2016.
 */
public class InsertRouteCoordinates extends AsyncTask<Void, Void, Boolean> {
    private String lat=null;
    private String route=null;
    private String longt=null;


    public InsertRouteCoordinates( String latitude, String routeid, String longitude){
        this.lat=latitude;
        this.longt=longitude;
        this.route=routeid;
    }

    @Override
    protected Boolean doInBackground(Void... params) {//TODO
        RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/adicionarcoordenada");
        client.AddParam("latitude", lat);
        client.AddParam("longitude", longt);
        client.AddParam("routeid", route);
        try {
            client.Execute(RequestMethod.POST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = client.getResponse();
        System.out.println(response);
        if(response.contains("OK")){
            return true;
        }else return false;

    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            System.out.println("Nova coordenada adicionada");
        } else {
            System.out.println("Falha na adição da nova rota");
        }
    }

}
