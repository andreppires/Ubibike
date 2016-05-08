package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;

/**
 * Created by wefbak on 07/05/2016.
 */
public class SimWifiP2pBroadcastReceiver extends BroadcastReceiver {

    private GPSTrackingApp tApp;
    private MsgSenderActivity mActivity;

    public SimWifiP2pBroadcastReceiver(MsgSenderActivity activity) {
        super();
        this.mActivity = activity;
    }

    public SimWifiP2pBroadcastReceiver(GPSTrackingApp trackingApp) {
        super();
        this.tApp = trackingApp;
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
                if(tApp == null) {
                    Toast.makeText(mActivity, "WiFi Direct enabled",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(tApp, "WiFi Direct enabled",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                if(tApp == null) {
                    Toast.makeText(mActivity, "WiFi Direct disabled",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(tApp, "WiFi Direct disabled",
                            Toast.LENGTH_SHORT).show();
                }

            }

        } else if (SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()

            if(tApp == null) {
                Toast.makeText(mActivity, "Peer list changed",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(tApp, "Peer list changed",
                        Toast.LENGTH_SHORT).show();
            }



        } else if (SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION.equals(action)) {

            SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
                    SimWifiP2pBroadcast.EXTRA_GROUP_INFO);
            ginfo.print();

            if(tApp == null) {
                Toast.makeText(mActivity, "Network membership changed",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(tApp, "Network membership changed",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION.equals(action)) {

            SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
                    SimWifiP2pBroadcast.EXTRA_GROUP_INFO);
            ginfo.print();

            if(tApp == null) {
                Toast.makeText(mActivity, "Group ownership changed",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(tApp, "Group ownership changed",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}
