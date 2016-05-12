package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class MyProfile extends FragmentActivity {
    Button mButton;
    ArrayList<String> arr_friends = new ArrayList<String>();
    Button deleteAcc;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);



        mButton = (Button)findViewById(R.id.deleteAcc);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        AlertDFragment alertdFragment = new AlertDFragment();
                        alertdFragment.show(fm, "Alert Dialog Fragment");

                    }
                });

        mButton = (Button)findViewById(R.id.changePassword);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        changePassword();
                    }

                }
        );

        TextView usernameView = (TextView)findViewById(R.id.username);
        usernameView.setText(Client.getClient().getUsername());

        TextView pointsView = (TextView)findViewById(R.id.numOfPoints);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(Client.getClient().getPontos());
        String strI = sb.toString();
        pointsView.setText(strI);
    }


    public void showFriends(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }


    public void changePassword(){
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
    }



}
