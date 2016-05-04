package pt.ulisboa.tecnico.cmov.ubibike.App;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.ubibike.SimWifiP2pBroadcastReceiver;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.PeerListListener;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.GroupInfoListener;

/**
 * Created by wefbak on 04/05/2016.
 */
public class WifiApp extends Application implements
        PeerListListener, GroupInfoListener {

    public static WifiApp singleton;
    private SimWifiP2pDeviceList peers = null;
    public SimWifiP2pInfo simWifiP2pInfo = null;
    public boolean hasAllUsers = false;
    private ArrayList<SimWifiP2pSocket> socketArrayList = new ArrayList<>();
    private ArrayList<Peers> connectedPeersList = null;
    public SimWifiP2pDeviceList devices;
    public boolean GO = false;
    public boolean inGroup = false;
    public String runningDevice;
    public String groupOwner = null;
    public PeerListListener listener;

    public WifiApp getInstance(){
        return singleton;
    }

    //WIFI DIRECT
    private boolean wifiEnabled = false;
    private ServiceConnection mConnection;
    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private Messenger mService = null;
    private boolean mBound = false;

    @Override
    public void onCreate() {
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

    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {
        StringBuilder peersStr = new StringBuilder();

        //compile list of devices in range
        for (SimWifiP2pDevice device : peers.getDeviceList()) {
            String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
            peersStr.append(devstr);
            Log.v("TODOS OS UTLIS?", devstr);
        }

        // display list of devices
        new AlertDialog.Builder(this)
                .setTitle("Devices in WiFi Network")
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
        this.devices = devices;
        this.simWifiP2pInfo = groupInfo;

        // compile list of network members
        StringBuilder peersStr = new StringBuilder();
        for (String deviceName : groupInfo.getDevicesInNetwork()) {
            SimWifiP2pDevice device = devices.getByName(deviceName);
            String devstr = "" + deviceName + " (" +
                    ((device == null)?"??":device.getVirtIp()) + ")\n";
            peersStr.append(devstr);
        }
        String users = peersStr.toString();
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

    public void setmManager(SimWifiP2pManager mManager) {
        this.mManager = mManager;
    }

    public SimWifiP2pManager.Channel getmChannel() {
        return mChannel;
    }

    public void setmChannel(SimWifiP2pManager.Channel mChannel) {
        this.mChannel = mChannel;
    }
    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public SimWifiP2pDeviceList getPeers() {
        return peers;
    }

    public void setPeers(SimWifiP2pDeviceList peers) {
        this.peers = peers;
    }

    public ArrayList<Peers> getConnectedPeersList() {
        return connectedPeersList;
    }

    public void setConnectedPeersList(ArrayList<Peers> connectedPeersList) {
        this.connectedPeersList = connectedPeersList;
    }

    public ArrayList<SimWifiP2pSocket> getSocketArrayList() {
        return socketArrayList;
    }

    public void setSocketArrayList(ArrayList<SimWifiP2pSocket> socketArrayList) {
        this.socketArrayList = socketArrayList;
    }

}
