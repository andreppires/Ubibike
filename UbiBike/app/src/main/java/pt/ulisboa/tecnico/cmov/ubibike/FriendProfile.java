package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FriendProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MsgSenderActivity.class);
        startActivity(intent);
    }

    public void givePoints(View view) {
        Intent intent = new Intent(this, GivePointsActivity.class);
        startActivity(intent);
    }

}
