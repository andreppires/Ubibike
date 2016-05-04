package pt.ulisboa.tecnico.cmov.ubibike;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    EditText oldPassword;
    EditText newpassword;
    Button mButton;
    String oldPass;
    String newPass;

    private UserLoginTask mAuthTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePass();
    }

    public void changePass() {
        oldPassword = (EditText) findViewById(R.id.oldPasswordEdit);

        newpassword = (EditText)findViewById(R.id.newPasswordEdit);

        mButton= (Button) findViewById(R.id.okNewPassword);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPass = oldPassword.getText().toString();
                System.out.println("oldPass= "+oldPass);

                newPass = newpassword.getText().toString();
                System.out.println("newPass= "+newPass);

                AcaoButao();
            }

        });

    }

    void AcaoButao(){
        mAuthTask= new UserLoginTask(Client.getClient().getUsername(),oldPass, newPass);
        mAuthTask.execute();

    }



//    Assyng tasks para fazer as coisinhas com o server
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    private final String mNewPassword;


    UserLoginTask(String email, String password, String newpassword) {
        mEmail = email;
        mPassword = password;
        mNewPassword = newpassword;
    }


    @Override
    protected Boolean doInBackground(Void... params) {

        RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/user");
        client.AddParam("username", mEmail);
        try {
            client.Execute(RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = client.getResponse();
        System.out.println("1ªresposta: "+response);

        if(response.contains(":")){
            String[] aux= response.split(":");
            String[] aux1= aux[1].split("\"");
            System.out.println("1= "+mPassword);
            System.out.println("2= "+aux1[1]);
            if(mPassword.equals(aux1[1])){
                //set new password
                System.out.println("estou aqui PAH!");
                client = new RestClient("http://andrepirespi.duckdns.org:3000/updatepass");
                client.AddParam("username", mEmail);
                System.out.println("username= "+mEmail);
                client.AddParam("novapassword", mNewPassword);
                System.out.println("novapassword= "+mNewPassword);

                try {
                    client.Execute(RequestMethod.POST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response = client.getResponse();
                System.out.println("2ªresposta: "+response);

                if(response.contains("OK")){
                    return true;
                }else return false;
            }else return false;
        }
        else return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {//todo


        if (success) {

            //Toast.makeText(this.getContext(), "Não existem pontos para enviar", Toast.LENGTH_SHORT).show();
            newpassword.setError("Password Updated");
        } else {
            oldPassword.setError("Wrong Password");
        }
    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}
}
