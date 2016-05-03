package pt.ulisboa.tecnico.cmov.ubibike;

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
        changePassword();
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

    public void changePassword() {
        Button okPass = (Button)findViewById(R.id.buttonOkPass);

        okPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText password = (EditText) findViewById(R.id.pass);

                String newPassword = password.getText().toString();

                System.out.println(newPassword);



                Client.getClient().setUsername(newPassword);
            }
        });
    }

}
