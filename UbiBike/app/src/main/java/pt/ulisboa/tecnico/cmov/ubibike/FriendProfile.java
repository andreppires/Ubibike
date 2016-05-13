package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FriendProfile extends AppCompatActivity {
    GetPoints mAuthTask = null;
    TextView friendPoints;
    String friendUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);


        Intent intent= getIntent();

        Bundle b = intent.getExtras();

        friendUsername = b.getString("STRING_I_NEED");
        GetPoints vailabuscar = new GetPoints(friendUsername);
        vailabuscar.execute();

        TextView friendNameView = (TextView) findViewById(R.id.friendUsernameView);

        friendPoints = (TextView) findViewById(R.id.numOfPointsView);

        friendNameView.setText(friendUsername);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MsgSenderActivity.class);
        startActivity(intent);
    }

    public void givePoints(View view) {
        Intent intent = new Intent(this, GivePointsActivity.class);
        intent.putExtra("STRING_I_NEED", friendUsername);
        startActivity(intent);
    }


    class GetPoints extends AsyncTask<Void, Void, Boolean> {

        private String email=null;
        int pontos=0;

        public GetPoints(String e){
            this.email=e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/pontos");
            client.AddParam("username", email);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            if(response.contains("pontos")){

                String[] aux= response.split(":");

                String[] aux1= aux[1].split("\\}");
                pontos = Integer.parseInt(aux1[0]);

                return true;

            }else return false;

        }


        @Override

        protected void onPostExecute(final Boolean success) {

            if (success) {

                friendPoints.setText(Integer.toString(pontos));

            } else {

            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
