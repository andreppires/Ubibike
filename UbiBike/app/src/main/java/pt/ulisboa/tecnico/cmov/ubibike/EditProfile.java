package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //changeUsername();
        changePass();
    }

    /*public void changeUsername() {
        Button okUsername = (Button)findViewById(R.id.buttonOkUsername);

        okUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView username = (TextView) findViewById(R.id.username);

                String newUsername = username.getText().toString();

                System.out.println(newUsername);

                Client.getClient().setUsername(newUsername);

            }
        });
    }*/



        public void changePass () {

            Button changepass = (Button) findViewById(R.id.buttonChangePassword);

            changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazCenas();
                }

            });
        }

    private void fazCenas() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }
}
