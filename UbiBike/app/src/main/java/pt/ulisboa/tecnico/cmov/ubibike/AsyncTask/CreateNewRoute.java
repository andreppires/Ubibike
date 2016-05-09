package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.ubibike.RequestMethod;
import pt.ulisboa.tecnico.cmov.ubibike.RestClient;
import pt.ulisboa.tecnico.cmov.ubibike.Stations;

/**
 * Created by andreppires on 05-05-2016.
 */
public class CreateNewRoute extends AsyncTask<Void, Void, Boolean> {
    private String username=null;
    private String bikeid=null;


    public CreateNewRoute(String username, String bikeid){
        this.username=username;
        this.bikeid=bikeid;
    }

    @Override
    protected Boolean doInBackground(Void... params) {//TODO
        RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/newroute");
        client.AddParam("username", username);
        client.AddParam("bikeid", bikeid);
        try {
            client.Execute(RequestMethod.POST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = client.getResponse();
        System.out.println(response);
        if(response.contains("Wrong query")){
            return false;
        }else{

            Stations.getStations().setRouteID(response);
            return true;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            System.out.println("Nova rota criada com sucesso!Rota= "+ Stations.getStations().getRouteID());


        } else {
            System.out.println("Falha na criação de nova rota. ");

        }
    }

}
