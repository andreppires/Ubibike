package pt.ulisboa.tecnico.cmov.ubibike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePass();
    }

    public void changePass() {
        EditText oldPassword = (EditText) findViewById(R.id.oldPasswordEdit);
        String oldPass = oldPassword.getText().toString();
        //Verificar se a pass corresponde a que ta na base de dados


        EditText newpassword = (EditText)findViewById(R.id.newPasswordEdit);
        String newPass = newpassword.getText().toString();
        //Client.getClient().setUsername(newPassword); Trocar a password

    }
}
