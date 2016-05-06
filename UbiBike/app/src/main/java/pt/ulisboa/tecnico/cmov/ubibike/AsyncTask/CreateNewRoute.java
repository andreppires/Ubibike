package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.ubibike.RequestMethod;
import pt.ulisboa.tecnico.cmov.ubibike.RestClient;

/**
 * Created by andreppires on 05-05-2016.
 */
public class CreateNewRoute extends AsyncTask<Void, Void, Boolean> {
    private String email=null;
    private String lat=null;
    private String route=null;
    private String longt=null;


    public CreateNewRoute(String username, String latitude, String routeid, String longitude){
        this.email=username;
        this.lat=latitude;
        this.longt=longitude;
        this.route=routeid;
    }

    @Override
    protected Boolean doInBackground(Void... params) {//TODO
        RestClient client = new RestClient("http://10.0.2.3:3000/coordenada");
        client.AddParam("username", email);
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

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {

        } else {

        }
    }

}
