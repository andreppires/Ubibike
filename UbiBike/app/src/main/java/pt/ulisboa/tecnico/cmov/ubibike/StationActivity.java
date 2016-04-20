package pt.ulisboa.tecnico.cmov.ubibike;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Button b = (Button) findViewById(R.id.button5);
        final TextView tx = (TextView) findViewById(R.id.textView9);

        // TODO Mostrar coordenadas GPS na Text View de todas as estações

        // TODO Longo clique na estação envia coordenadas para o Google Maps dessa estação

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                intent.setData(Uri.parse("google.navigation:q=38.737205, -9.302388"));

                Intent chooser = intent.createChooser(intent, "Set");

                startActivity(chooser);
            }
        });

    }
}
