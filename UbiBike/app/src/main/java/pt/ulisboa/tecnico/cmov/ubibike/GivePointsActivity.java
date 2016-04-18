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

        final EditText et = (EditText) findViewById(R.id.editText);
        final TextView tx = (TextView) findViewById(R.id.textView7);
        final TextView tx8 = (TextView) findViewById(R.id.textView8);
        Button b = (Button)findViewById(R.id.button);

        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        tx8.setInputType(InputType.TYPE_CLASS_NUMBER);
        tx.setInputType(InputType.TYPE_CLASS_NUMBER);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int friendsPoints = Integer.parseInt(tx.getText().toString());
                final int pointsToSend = Integer.parseInt(et.getText().toString());
                final int myPoints = Integer.parseInt(tx8.getText().toString());

                if(myPoints > 0) {
                    tx.setText(Integer.toString(friendsPoints + pointsToSend));
                    tx8.setText(Integer.toString(myPoints - pointsToSend));
                } else {

                    Toast.makeText(v.getContext(), "Não existem pontos para enviar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



}
