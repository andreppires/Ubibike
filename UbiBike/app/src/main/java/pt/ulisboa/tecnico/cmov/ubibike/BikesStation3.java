package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class BikesStation3 extends AppCompatActivity {

    GetBikes vaiLaBuscar=null;
    PostPickUp postPickUp = null;
    ArrayList<String> bikes = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_station3);

        getBikesList();
    }

    public void getBikesList() {
        vaiLaBuscar= new GetBikes("Store3");
        vaiLaBuscar.execute();
    }

    /* Ligação ao server
     */

    class GetBikes extends AsyncTask<Void, Void, Boolean> {

        private String stationid=null;
        public GetBikes(String e){
            this.stationid=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/bike");
            client.AddParam("stationid", stationid);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();

            System.out.println(response);
            if (response.contains(",")) {
                String[] aux= response.split(",");

                for (int i=0; i < aux.length ; i++ ) {
                    String[] st = aux[i].split("\"");
                    bikes.add(i, st[3]);
                }
                return true;
            } else if (response.contains("{")) {
            String[] st = response.split("\"");
            bikes.add(st[3]);
            return true;
        } else
                return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                vaiLaBuscar=null;
                coiso();
            } else {
                runOnUiThread(new Runnable()
                {
                    public void run() {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Não existem bicicletas disponíveis nesta estação", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        protected void onCancelled() {
            vaiLaBuscar = null;
        }
    }

    protected void coiso () {

        ListView listBikesStation3= (ListView) findViewById(R.id.listOfBikesInStation1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BikesStation3.this, android.R.layout.simple_list_item_1, bikes);
        listBikesStation3.setAdapter(adapter);

        listBikesStation3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bikeIP = ((TextView)view).getText().toString();
                Stations.getStations().setBiclaIP(bikeIP);
                Intent intent = new Intent(BikesStation3.this, RoutingTime.class);
                intent.putExtra("STRING_I_NEED", bikeIP);
                startActivity(intent);
            }
        } );
    }

    class PostPickUp extends AsyncTask<Void, Void, Boolean>{

        private final String bikeid;

        PostPickUp(String pickedUpbikeid) {
            bikeid = pickedUpbikeid;
        }

        @Override
        protected Boolean doInBackground(Void... params) { //todo apenas actualiza os pontos do atual utilizador. Pontos do amigo também têm de ser atualizados.
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/pickUp");
            client.AddParam("bikeid", bikeid);

            try {
                client.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();

            if(!response.contains("OK")){
                return true;
            }else {
                return false;
            }
        }

        @Override
        protected void onCancelled() {
            postPickUp = null;
        }
    }


}
