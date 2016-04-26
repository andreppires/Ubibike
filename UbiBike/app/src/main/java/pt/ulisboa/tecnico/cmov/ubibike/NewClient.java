package pt.ulisboa.tecnico.cmov.ubibike;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewClient extends AppCompatActivity {

    private EditText mEmail = null;
    private EditText mPassword = null;
    private String email;
    private String passowrd;
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
                        passowrd = mPassword.getText().toString().trim();
                        System.out.println(email);
                        System.out.println(passowrd);

                    }
                });
    }
    class PostClient extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }
}
