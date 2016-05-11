package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.ubibike.Client;
import pt.ulisboa.tecnico.cmov.ubibike.R;
import pt.ulisboa.tecnico.cmov.ubibike.RequestMethod;
import pt.ulisboa.tecnico.cmov.ubibike.RestClient;

/**
 * Created by andreppires on 11-05-2016.
 */
public class SetPoints extends AsyncTask<Void, Void, Boolean> {

    private final String mmEmail;
    private final int mPontos;
    public SetPoints(String email, int pontos) {
        mmEmail = email;
        mPontos = pontos;
    }

    SetPoints connectServer;

    @Override
    protected Boolean doInBackground(Void... params) {
        RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/setpoints");
        client.AddParam("username", mmEmail);
        client.AddParam("points", Integer.toString(mPontos) );
        try {
            client.Execute(RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = client.getResponse();

        if(!response.contains("OK")){
            Client.getClient().setPontos(mPontos);
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        connectServer = null;

        if (success) {

            System.out.println("Pontos enviados para o servidor");
        } else {
            System.out.println("Falha no envio dos pontos para o servidor");
        }
    }

    @Override
    protected void onCancelled() {
        connectServer = null;
    }
}
