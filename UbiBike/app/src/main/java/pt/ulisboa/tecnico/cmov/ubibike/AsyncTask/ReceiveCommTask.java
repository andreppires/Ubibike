package pt.ulisboa.tecnico.cmov.ubibike.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.ubibike.App.WifiApp;
import pt.ulisboa.tecnico.cmov.ubibike.App.Peers;
import pt.ulisboa.tecnico.cmov.ubibike.App.Message;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CacheResponse;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;

public class ReceiveCommTask extends AsyncTask<SimWifiP2pSocket, String, Void> {
    SimWifiP2pSocket s;
    Context mContext;
    WifiApp app;

    public ReceiveCommTask(WifiApp wifiApp, Context context) {
        this.app = wifiApp;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(SimWifiP2pSocket... params) {
        ObjectInputStream sockIn;
        ObjectOutputStream sockOut;
        String st;

        s = params[0];
        try {

            sockIn = new ObjectInputStream(s.getInputStream());

            Message msg;
            Object obj;
            while ((obj = sockIn.readObject()) != null) {

                if (obj instanceof Message) {

                    msg = (Message) obj;

                    String request = msg.getMessage();

                    if (request.equals("requestIp")) {
                        if (app.GO) {
                            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                            Peers pGO = msg.getConnectedPeersList().get(0);

                            Message reqOrder = new Message("peerList");
                            reqOrder.getConnectedPeersList().add(pGO);
                            reqOrder.getConnectedPeersList().addAll(app.getConnectedPeersList());

                            out.writeObject(reqOrder);
                        }
                        break;
                    }
                    if (request.equals("peerList")) {
                        msg = (Message) obj;
                        app.setConnectedPeersList(msg.getConnectedPeersList());
                        break;
                    }
                    break;
                }

            }
        } catch (IOException e) {
            Log.d("Error reading socket:", e.getMessage());
        }/* catch (JSONException e) {
                Log.d("Error parsing object:", e.getMessage());
            }*/ catch (ClassNotFoundException e) {
            Log.d("Class not found", e.getMessage());
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        if (!s.isClosed()) {
            try {
                s.close();
            } catch (Exception e) {
                Log.d("Error closing socket:", e.getMessage());
            }
        }
        s = null;
    }

}