package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void myProfileActivity(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void bikesActivity(View view) {
        Intent intent = new Intent(this, BikesNeirby.class);
        startActivity(intent);
    }

    public void friendsActivity(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    //public void stationActivity(View view) { //TODO quando se tiver a activity das estações - nome deve ser StationActivity
    //    Intent intent = new Intent(this, StationActivity.class);
    //    startActivity(intent);
    //}
}
