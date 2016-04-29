package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        mButton = (Button)findViewById(R.id.deleteAcc);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        DeleteAyncAccount deletaAi = new DeleteAyncAccount(Client.getClient().getUsername());
                        deletaAi.execute();
                        login();

                    }
                });

        mButton = (Button)findViewById(R.id.editProfile);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        editProfile();
                    }

                }
        );

        TextView usernameView = (TextView)findViewById(R.id.username);
        System.out.println(Client.getClient().getUsername());
        usernameView.setText(Client.getClient().getUsername());

        TextView pointsView = (TextView)findViewById(R.id.numOfPoints);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(Client.getClient().getPontos());
        String strI = sb.toString();
        pointsView.setText(strI);
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void showFriends(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    class DeleteAyncAccount extends AsyncTask<Void, Void, Boolean> {

        String mEmail;

        public DeleteAyncAccount(String p){
            this.mEmail=p;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/userdelete");
            client.AddParam("username", mEmail);
            try {
                client.Execute(RequestMethod.POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.getResponse();
            return null;
        }
    }

    public void editProfile(){
            Intent intent = new Intent(this, EditProfile.class);
            startActivity(intent);

    }
}
