package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import java.util.ArrayList;
import java.util.HashMap;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;

/**
 * Created by wefbak on 08/05/2016.
 */
public class GPSTrackingApp extends Application implements
        SimWifiP2pManager.PeerListListener, SimWifiP2pManager.GroupInfoListener {

        public static GPSTrackingApp singleton;

        private SimWifiP2pSocketServer mSrvSocket = null;
        private SimWifiP2pSocket mCliSocket = null;
        private SimWifiP2pBroadcastReceiver mReceiver;

        public GPSTrackingApp getInstance(){
                return singleton;
        }

        //WIFI DIRECT
        private boolean wifiEnabled = false;
        private ServiceConnection mConnection;
        private SimWifiP2pManager mManager = null;
        private SimWifiP2pManager.Channel mChannel = null;
        private Messenger mService = null;
        private boolean mBound = false;

        public void onCreate(){
                super.onCreate();
                singleton = this;

                mConnection = new ServiceConnection() {
                        // callbacks for service binding, passed to bindService()

                        @Override
                        public void onServiceConnected(ComponentName className, IBinder service) {
                                mService = new Messenger(service);
                                mManager = new SimWifiP2pManager(mService);
                                mChannel = mManager.initialize(getApplicationContext(), getMainLooper(), null);
                                mBound = true;
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName arg0) {
                                mService = null;
                                mManager = null;
                                mChannel = null;
                                mBound = false;
                        }
                };

                // initialize the WDSim API
                SimWifiP2pSocketManager.Init(getApplicationContext());

                // register broadcast receiver
                IntentFilter filter = new IntentFilter();
                filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
                filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
                filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
                filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
                SimWifiP2pBroadcastReceiver receiver = new SimWifiP2pBroadcastReceiver(this);
                registerReceiver(receiver, filter);

                Intent intent = new Intent(getApplicationContext(), SimWifiP2pService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                mBound = true;
        }



        //Getters and Setters

        public boolean ismBound() {
                return mBound;
        }

        public void setmBound(boolean mBound) {
                this.mBound = mBound;
        }

        public ServiceConnection getmConnection() {
                return mConnection;
        }

        public SimWifiP2pManager getmManager() {
                return mManager;
        }

        public SimWifiP2pManager.Channel getmChannel() {
                return mChannel;
        }

        public void setWifiEnabled(boolean wifiEnabled) {
                this.wifiEnabled = wifiEnabled;
        }

        /*
	 * Listeners associated to Termite
	 */

        @Override
        public void onPeersAvailable(SimWifiP2pDeviceList peers) {
                StringBuilder peersStr = new StringBuilder();

                // compile list of devices in range
                for (SimWifiP2pDevice device : peers.getDeviceList()) {
                        String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
                        peersStr.append(devstr);
                }

                // display list of devices in range
                new AlertDialog.Builder(this)
                        .setTitle("Devices in WiFi Range")
                        .setMessage(peersStr.toString())
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                        })
                        .show();
        }

        @Override
        public void onGroupInfoAvailable(SimWifiP2pDeviceList devices,
                                         SimWifiP2pInfo groupInfo) {

                // compile list of network members
                StringBuilder peersStr = new StringBuilder();
                for (String deviceName : groupInfo.getDevicesInNetwork()) {
                        SimWifiP2pDevice device = devices.getByName(deviceName);
                        String devstr = "" + deviceName + " (" +
                                ((device == null)?"??":device.getVirtIp()) + ")\n";
                        peersStr.append(devstr);
                }

                // display list of network members
                new AlertDialog.Builder(this)
                        .setTitle("Devices in WiFi Network")
                        .setMessage(peersStr.toString())
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                        })
                        .show();
        }
}
