package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowThatRoute extends AppCompatActivity {
    public static final String KEY_EXTRA = "com.example.UbiBike.KEY_BOOK";
    String yourDataObject=null;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_that_route);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String name = b.getString("KEY_EXTRA");
        getCoordenates(name);

    }

    private void getCoordenates(String routeid) {
        GetCoordenates connect = new GetCoordenates(routeid);
        connect.execute();
    }


    class GetCoordenates extends AsyncTask<Void, Void, Boolean> {

        private String routeid = null;

        private String response;

        public GetCoordenates(String e) {
            this.routeid = e;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient client = new RestClient("http://andrepirespi.duckdns.org:3000/getCoordinatesFromRoute");
            client.AddParam("routeid", routeid);
            try {
                client.Execute(RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response = client.getResponse();

            if (response.contains("\"")) {
                return true;
            } else return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                parseAndSet(response);
            }
        }
    }

    private void parseAndSet(String response) {
        ArrayList<Location> coordenadas = new ArrayList<>();
        if (response.contains(",")) {
            String[] aux = response.split("\\}");
            String[] coisinhoNovo;
            String[] coisinhoNovoNovo;
            //primeiro caso
            String[] st = aux[0].split(",");
            coisinhoNovo= st[0].split(":");
            coisinhoNovoNovo= st[1].split(":");
            Location primas= new Location("coiso");
            primas.setLatitude(Double.parseDouble(coisinhoNovo[1]));
            primas.setLongitude(Double.parseDouble(coisinhoNovoNovo[1]));
            coordenadas.add(primas);
            LatLng IST = new LatLng(coordenadas.get(0).getLatitude(), coordenadas.get(0).getLongitude());
            for (int i = 1; i < aux.length-1; i++) {
                st = aux[i].split(",");
                coisinhoNovo= st[1].split(":");
                coisinhoNovoNovo= st[2].split(":");
                primas.setLatitude(Double.parseDouble(coisinhoNovo[1]));
                primas.setLongitude(Double.parseDouble(coisinhoNovoNovo[1]));
                coordenadas.add(primas);
            }
            ArrayList<String> aaa= new ArrayList<>();
            for(Location p: coordenadas)
                aaa.add(""+p.getLatitude()+", "+p.getLongitude());


            final ListView listBikesStation1= (ListView) findViewById(R.id.listCoordinates);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowThatRoute.this, android.R.layout.simple_list_item_1, aaa);
            listBikesStation1.setAdapter(adapter);
        }
    }
}
