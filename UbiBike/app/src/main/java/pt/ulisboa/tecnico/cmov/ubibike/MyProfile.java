package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyProfile extends AppCompatActivity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        mButton = (Button)findViewById(R.id.button8);

        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        DeleteAyncAccount deletaAi = new DeleteAyncAccount(Client.getClient().getUsername());
                        deletaAi.execute();

                    }
                });
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
            String response = client.getResponse();
            return null;
        }
    }
}
