package pt.ulisboa.tecnico.cmov.ubibike;

/**
 * Created by andreppires on 11-05-2016.
 */

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BikesStation extends AppCompatActivity {

    GetBikes vaiLaBuscar=null;
    PostPickUp postPickUp = null;
    ArrayList<String> bikes = new ArrayList<String>();
    String yourDataObject;

    public static final String KEY_EXTRA = "com.example.yourapp.KEY_BOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes_station1);

        yourDataObject = null;
        yourDataObject = getIntent().getStringExtra(KEY_EXTRA);

        getBikesList();


    }

    public void getBikesList() {
        vaiLaBuscar= new GetBikes(yourDataObject);
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

            }
            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                vaiLaBuscar=null;
                coiso();
            }
        }

        @Override
        protected void onCancelled() {
            vaiLaBuscar = null;
        }
    }

    protected void coiso () {

        ListView listBikesStation1= (ListView) findViewById(R.id.listOfBikesInStation1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BikesStation.this, android.R.layout.simple_list_item_1, bikes);
        listBikesStation1.setAdapter(adapter);

        listBikesStation1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bikeIP = ((TextView)view).getText().toString();

                Stations.getStations().setBiclaIP(bikeIP);
                PostPickUp postPickUp = new PostPickUp(bikeIP);
                postPickUp.execute();

                Intent intent = new Intent(BikesStation.this, RoutingTime.class);
                intent.putExtra("BIKEIP", bikeIP);

                Stations station = new Stations();
                Location stationlocal = station.getStation1();


                startActivity(intent);
            }
        });
    }


    class PostPickUp extends AsyncTask<Void, Void, Boolean>{

        private final String bikeid;

        PostPickUp(String pickedUpbikeid) {
            bikeid = pickedUpbikeid;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
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
