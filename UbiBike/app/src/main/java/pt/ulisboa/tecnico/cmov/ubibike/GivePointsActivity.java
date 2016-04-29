package pt.ulisboa.tecnico.cmov.ubibike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GivePointsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_give_points);

        final EditText pointstosend = (EditText) findViewById(R.id.pointsToSend);
        final TextView friendpoints = (TextView) findViewById(R.id.friendPoints);
        final TextView mypoints = (TextView) findViewById(R.id.myPoints);
        Button b = (Button)findViewById(R.id.button);

        pointstosend.setInputType(InputType.TYPE_CLASS_NUMBER);
        mypoints.setInputType(InputType.TYPE_CLASS_NUMBER);
        friendpoints.setInputType(InputType.TYPE_CLASS_NUMBER);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int friendsPoints = Integer.parseInt(friendpoints.getText().toString());
                final int pointsToSend = Integer.parseInt(pointstosend.getText().toString());
                final int myPoints = Integer.parseInt(mypoints.getText().toString());

                if(myPoints > 0) {
                    friendpoints.setText(Integer.toString(friendsPoints + pointsToSend));
                    mypoints.setText(Integer.toString(myPoints - pointsToSend));
                } else {

                    Toast.makeText(v.getContext(), "Não existem pontos para enviar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}
