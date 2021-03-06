package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;


public class HomeActivity extends AppCompatActivity {

    private GPSTrackingApp mApp;

    public int update = 0;

    protected void onCreate(Bundle savedInstanceState) {
        GetStations getsts = new GetStations();
        getsts.execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mApp = (GPSTrackingApp) getApplicationContext();

        //wifiOn();

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
        Intent intent = new Intent(this, StationsActivity.class);
        startActivity(intent);
    }

    public void wifiOn() {
        final GPSTrackingApp app = (GPSTrackingApp) getApplicationContext();

        if (!app.ismBound()) {
            Intent intent = new Intent(this, SimWifiP2pService.class);
            app.bindService(intent, app.getmConnection(), Context.BIND_AUTO_CREATE);
            app.setmBound(true);
            Toast.makeText(this, "Wifi Bounded", Toast.LENGTH_SHORT);
        }else {
            if (!app.ismBound())
                Toast.makeText(this, "Service already bound",
                        Toast.LENGTH_SHORT).show();
        }
    }

    class GetStations extends AsyncTask<Void, Void, Boolean> {
        String response;


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/availableStations");

            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response = client.getResponse();

            if (response.contains("\""))
            {


                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                String[] aux= response.split(",");

                for (int i=0; i < aux.length ; i++ ) {
                    String[] st = aux[i].split("\"");
                    Stations.getStations().addStationsList(st[3]);
                }
                System.out.println("Consegui obter as estações ");

            } else {
                System.out.println("Não consegui obter as estações ");
                GetStations getsts = new GetStations();
                getsts.execute();

            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}

