package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.ubibike.Client;
import pt.ulisboa.tecnico.cmov.ubibike.R;
import pt.ulisboa.tecnico.cmov.ubibike.RequestMethod;
import pt.ulisboa.tecnico.cmov.ubibike.RestClient;

/**
 * Created by andreppires on 11-05-2016.
 */
public class LeaveBike extends AsyncTask<Void, Void, Boolean> {
    private final String biclaToLeave;
    private LeaveBike connectServer;


    public LeaveBike(String bicla){
        this.biclaToLeave = bicla;
    }

@Override
protected Boolean doInBackground(Void... params) {
        RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/leaveBike");
    client.AddParam("bikeid", biclaToLeave);
        try {
        client.Execute(RequestMethod.GET);
        } catch (Exception e) {
        e.printStackTrace();
        }
        String response = client.getResponse();

        if(response.contains("OK")){
            return true;
        }else {
            return false;
        }
        }

@Override
protected void onPostExecute(final Boolean success) {
        connectServer = null;

        if (success) {
            System.out.println("Bicicleta atualizada");
        } else {
            System.out.println("não conseguiu mudar o estado da bicla");
        }
        }

@Override
protected void onCancelled() {
        connectServer = null;
        }
        }