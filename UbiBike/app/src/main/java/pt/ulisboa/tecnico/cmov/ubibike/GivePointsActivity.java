package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;


public class GivePointsActivity extends AppCompatActivity {

    SetPoints connectServer = null;


    EditText pointstosend;
    TextView mypoints;
    TextView friendpoints;
    TextView friend;

    int friendsPoints;
    int pointsToSend;
    int myPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_points);

        pointstosend = (EditText) findViewById(R.id.pointsToSend);
        friendpoints = (TextView) findViewById(R.id.friendPoints);
        mypoints = (TextView) findViewById(R.id.myPoints);
        Button b = (Button)findViewById(R.id.button);
        friend = (TextView) findViewById(R.id.friendUsername);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        String friendName = bundle.getString("STRING_I_NEED");
        friend.setText(friendName);


        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(Client.getClient().getPontos());
        String strI = sb.toString();
        mypoints.setText(strI);

        pointstosend.setInputType(InputType.TYPE_CLASS_NUMBER);
        mypoints.setInputType(InputType.TYPE_CLASS_NUMBER);
        friendpoints.setInputType(InputType.TYPE_CLASS_NUMBER);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myPoints = Client.getClient().getPontos();
                pointsToSend = Integer.parseInt(pointstosend.getText().toString());

//                friendsPoints = Integer.parseInt(friendpoints.getText().toString());
//                pointsToSend = Integer.parseInt(pointstosend.getText().toString());
//                myPoints = Integer.parseInt(mypoints.getText().toString());

                if(myPoints > pointsToSend) {
                    connectServer = new SetPoints(Client.getClient().getUsername(), (myPoints-pointsToSend));
                    connectServer.execute();
                } else {
                    Toast.makeText(v.getContext(), "Não existem pontos para enviar", Toast.LENGTH_SHORT).show();
                }
                Log.d("GIVEPOINTS","Pontos do utilizador :" + Client.getClient().getPontos());
            }
        });
    }

    class SetPoints extends AsyncTask<Void, Void, Boolean>{

        private final String mmEmail;
        private final int mPontos;
        SetPoints(String email, int pontos) {
            mmEmail = email;
            mPontos = pontos;
        }

        @Override
        protected Boolean doInBackground(Void... params) { //todo apenas actualiza os pontos do atual utilizador. Pontos do amigo também têm de ser atualizados.
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/setpoints");
            client.AddParam("username", mmEmail);
            client.AddParam("points", Integer.toString(mPontos) );
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();

            if(!response.contains("OK")){
                Client.getClient().setPontos(mPontos);
                return true;
            }else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            connectServer = null;

            if (success) {
                friendpoints.setText(Integer.toString(friendsPoints + pointsToSend));
                mypoints.setText(Integer.toString(myPoints - pointsToSend));


            } else {
                pointstosend.setError(getString(R.string.error_server));
            }
        }

        @Override
        protected void onCancelled() {
            connectServer = null;
        }
    }
}
