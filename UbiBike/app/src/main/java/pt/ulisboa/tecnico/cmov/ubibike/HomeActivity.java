package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.ulisboa.tecnico.cmov.ubibike.App.Message;
import pt.ulisboa.tecnico.cmov.ubibike.App.Peers;
import pt.ulisboa.tecnico.cmov.ubibike.App.WifiApp;

public class HomeActivity extends AppCompatActivity {

    private Button mButton;
    private WifiApp app;
    private SimWifiP2pSocketServer mSrvSocket = null;
    private SimWifiP2pBroadcastReceiver mServer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        app = (WifiApp) getApplication().getApplicationContext();
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
        Intent intent = new Intent(this, StationActivity.class);
        startActivity(intent);
    }

    public void wifiOn(View view) {
        final WifiApp app = (WifiApp) getApplicationContext();

        if (!app.ismBound()) {
            Intent intent = new Intent(view.getContext(), SimWifiP2pService.class);
            app.bindService(intent, app.getmConnection(), Context.BIND_AUTO_CREATE);
            app.setmBound(true);
            Toast.makeText(this, "Wifi Bounded", Toast.LENGTH_SHORT);
            Thread thread = new Thread() {
                public void run() {
                    try {

                        mSrvSocket = new SimWifiP2pSocketServer(9002);

                        while (true) {

                            final SimWifiP2pSocket cliSocket = mSrvSocket.accept();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            Toast.makeText(this, "Service bound", Toast.LENGTH_SHORT).show();
        } else {
            if (!app.ismBound())
                Toast.makeText(view.getContext(), "Service already bound",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void searchPeers(View view) {
        final WifiApp app = (WifiApp) getApplicationContext();

        if (!app.GO && app.ismBound() && app.inGroup) {

        } else {
            if (!app.ismBound())
                Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(this, "No Peers Connected", Toast.LENGTH_SHORT).show();
        }

    }

    public void wifiOff(View view) {
        if (app.ismBound()) {
            app.unbindService(app.getmConnection());
            app.setmBound(false);
            Toast.makeText(this, "Wifi unBounded", Toast.LENGTH_SHORT);
        }
    }
}
