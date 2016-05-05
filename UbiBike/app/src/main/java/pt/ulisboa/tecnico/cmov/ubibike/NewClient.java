package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLOutput;

public class NewClient extends AppCompatActivity {

    private EditText mEmail = null;
    private EditText mPassword = null;
    private String email;
    private String password;
    PostClient connectServer = null;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        mButton = (Button)findViewById(R.id.button7);
        mEmail = (EditText)findViewById(R.id.registoEmail);
        mPassword = (EditText)findViewById(R.id.editText5);


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        email = mEmail.getText().toString().trim();
                        password = mPassword.getText().toString().trim();
                        connectServer = new PostClient(email, password);
                        connectServer.execute();

                    }
                });
    }
    class PostClient extends AsyncTask<Void, Void, Boolean>{
        private final String mmEmail;
        private final String mmPassword;

        PostClient(String email, String password) {
            mmEmail = email;
            mmPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://10.0.2.3:3000/user");
            client.AddParam("username", mmEmail);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String response = client.getResponse();

            if(!response.contains(":")){

                client = new RestClient("http://10.0.2.3:3000/account");
                client.AddParam("username", mmEmail);
                client.AddParam("password", mmPassword);
                try {
                    client.Execute(RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response=client.getResponse();
                if(response.contains("OK")) {
                    Client.setClient(new Client(mmEmail, 0));
                    return true;
                }else {
                    return false;

                }
            }else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            connectServer = null;

            if (success) {
                finish();
                //Client.setClient(mmEmail, 0);
                homeActivity();
            } else {
                mEmail.setError(getString(R.string.error_email_exists));
            }
        }

        @Override
        protected void onCancelled() {
            connectServer = null;
        }
    }

    public void homeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
