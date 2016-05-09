package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.ulisboa.tecnico.cmov.ubibike.AsyncTask.CreateNewRoute;
import pt.ulisboa.tecnico.cmov.ubibike.AsyncTask.InsertRouteCoordinates;

public class HomeActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }

    public void myProfileActivity(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void bikesActivity(View view) {
        Intent intent = new Intent(this, BikesNeirby.class);
        startActivity(intent);
    }

    public void friendsActivity(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    public void stationActivity(View view) {
        GetStations getsts = new GetStations("stations");
        getsts.execute();
        Intent intent = new Intent(this, StationsActivity.class);
        startActivity(intent);
    }


    public void startRouteActivity(View view) {
        Intent intent = new Intent(this, RoutingTime.class);
        startActivity(intent);
    }




    class GetStations extends AsyncTask<Void, Void, Boolean> {

        private String stations=null;
        public GetStations(String e){
            this.stations=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/availableStations");

            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            System.out.println(response);

            if (response.contains(","))
            {
                String[] aux= response.split(",");

                for (int i=0; i < aux.length ; i++ ) {
                    String[] st = aux[i].split("\"");
                    Stations.getStations().addStationsList( st[3]);
                }

                System.out.println(Stations.getStations().getStationsList());
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
            } else {
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}
