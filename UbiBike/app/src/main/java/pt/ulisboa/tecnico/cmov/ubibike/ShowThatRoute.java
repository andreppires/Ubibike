package pt.ulisboa.tecnico.cmov.ubibike;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

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

            System.out.println(client.getResponse());
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
        System.out.println("resposta: " + response);
        List<Location> coordenadas = new ArrayList<>();
        if (response.contains(",")) {
            String[] aux = response.split("\\}");
            String[] coisinhoNovo;
            String[] coisinhoNovoNovo;
            //primeiro caso
            System.out.println("coidinho: "+aux[0]);
            String[] st = aux[0].split(",");
            coisinhoNovo= st[0].split(":");
            coisinhoNovoNovo= st[1].split(":");
            System.out.println("latitude= "+coisinhoNovo[1]);
            System.out.println("longitude= "+coisinhoNovoNovo[1]);
            Location primas= new Location("coiso");
            primas.setLatitude(Double.parseDouble(coisinhoNovo[1]));
            primas.setLongitude(Double.parseDouble(coisinhoNovoNovo[1]));
            coordenadas.add(primas);
            LatLng IST = new LatLng(coordenadas.get(0).getLatitude(), coordenadas.get(0).getLongitude());
            for (int i = 1; i < aux.length-1; i++) {
                System.out.println("coidinho: "+aux[i]);
                st = aux[i].split(",");
                coisinhoNovo= st[1].split(":");
                coisinhoNovoNovo= st[2].split(":");
                System.out.println("latitude= "+coisinhoNovo[1]);
                System.out.println("longitude= "+coisinhoNovoNovo[1]);
                primas.setLatitude(Double.parseDouble(coisinhoNovo[1]));
                primas.setLongitude(Double.parseDouble(coisinhoNovoNovo[1]));
                coordenadas.add(primas);
            }


            // Get a handle to the Map Fragment
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            System.out.println("coordendas[0]: latitude= "+coordenadas.get(1).getLatitude());
            System.out.println("coordendas[0]: longitude= "+coordenadas.get(1).getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(IST)                // Sets the center of the map to Mountain View
                    .zoom(12)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



            // Instantiates a new Polyline object and adds points to define a rectangle
            PolylineOptions rectOptions = new PolylineOptions();
            for (Location p:coordenadas) {
                rectOptions.add(new LatLng(p.getLatitude(), p.getLongitude()));
            }
            // Get back the mutable Polyline
            Polyline polyline = mMap.addPolyline(rectOptions);


        }
    }
}
