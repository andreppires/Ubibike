package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.ulisboa.tecnico.cmov.ubibike.App.Peers;
import pt.ulisboa.tecnico.cmov.ubibike.App.WifiApp;


public class SimWifiP2pBroadcastReceiver extends BroadcastReceiver {

    private WifiApp wifiApp;

    public SimWifiP2pBroadcastReceiver(WifiApp app) {
        super();
        this.wifiApp = app;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            // This action is triggered when the Termite service changes state:
            // - creating the service generates the WIFI_P2P_STATE_ENABLED event
            // - destroying the service generates the WIFI_P2P_STATE_DISABLED event

            int state = intent.getIntExtra(SimWifiP2pBroadcast.EXTRA_WIFI_STATE, -1);
            if (state == SimWifiP2pBroadcast.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(wifiApp, "WiFi Direct enabled",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(wifiApp, "WiFi Direct disabled",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()

            SimWifiP2pDeviceList deviceList = (SimWifiP2pDeviceList) intent.getSerializableExtra(SimWifiP2pBroadcast.EXTRA_DEVICE_LIST);
            wifiApp.setPeers(deviceList);

            wifiApp.inGroup = true;

            Toast.makeText(wifiApp, "Peer list changed",
                    Toast.LENGTH_SHORT).show();

        } else if (SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION.equals(action)) {

            SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
                    SimWifiP2pBroadcast.EXTRA_GROUP_INFO);

            ginfo.print();
            wifiApp.getmManager().requestGroupInfo(wifiApp.getmChannel(), (SimWifiP2pManager.GroupInfoListener) WifiApp.singleton);
            wifiApp.runningDevice = ginfo.getDeviceName();
            wifiApp.simWifiP2pInfo = ginfo;
            boolean first = true;
            ginfo.print();
            ArrayList<Peers> newPeersList = new ArrayList<>();
            for (String name : ginfo.getDevicesInNetwork()) {
                if ((wifiApp.getPeers().getByName(name) != null)) {

                    String virtualIP = wifiApp.getPeers().getByName(name).getVirtIp();
                    int virtualPort = wifiApp.getPeers().getByName(name).getVirtPort();
                    String pName = wifiApp.getPeers().getByName(name).deviceName;
                    newPeersList.add(new Peers(pName, virtualIP, virtualPort));
                    if (first && !wifiApp.GO) {
                        wifiApp.groupOwner = pName;
                        first = false;
                    }
                    wifiApp.hasAllUsers = false;
                }
            }
            wifiApp.setConnectedPeersList(newPeersList);
            for (Peers p : wifiApp.getConnectedPeersList()) {
                Log.v("connected peers", p.getDeviceName());
            }

            Toast.makeText(wifiApp, "Network membership changed",
                    Toast.LENGTH_SHORT).show();

        } else if (SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION.equals(action)) {

            SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
                    SimWifiP2pBroadcast.EXTRA_GROUP_INFO);
            ginfo.print();
            if (ginfo.askIsGO()) {
                wifiApp.GO = true;
            } else {
                wifiApp.GO = false;
            }
            Toast.makeText(wifiApp, "Group ownership changed",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
