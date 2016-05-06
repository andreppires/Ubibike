package pt.ulisboa.tecnico.cmov.ubibike;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.ubibike.App.Peers;
import pt.ulisboa.tecnico.cmov.ubibike.App.WifiApp;

public class RoutingTime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public LocationManager locationManager;
    public LocationUpdateListener listener;

    private ArrayList<Location> locationsRoute= new ArrayList<Location>();
    private double istLat=38.752694;
    private double istLong=-9.184699;
    private LatLng IST = new LatLng(istLat, istLong);
    private Location lastLocation=null;

    private boolean running=false;
    private boolean mightStopped=false;
    private boolean mightStarting=false;
    float distance=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing_time);

        // Get a handle to the Map Fragment
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        mMap.setMyLocationEnabled(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationUpdateListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(IST)                // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void requestPermissions() {//todo not entirety working
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    return;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    class LocationUpdateListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            Log.d("GPS", "Location Changed " + location.toString());
            double latitude= location.getLatitude();
            double longitude= location.getLongitude();
            System.out.println("latitude= "+ latitude+"\t longitude= "+longitude);


            setDistance(location);
            locationsRoute.add(location);

            // Instantiates a new Polyline object and adds points to define a rectangle
            PolylineOptions rectOptions = new PolylineOptions();
            for (Location p:locationsRoute) {
                rectOptions.add(new LatLng(p.getLatitude(), p.getLongitude()));
            }
            // Get back the mutable Polyline
            Polyline polyline = mMap.addPolyline(rectOptions);

            ////////////////////////////////////////////
            //Check if it is running or not
            /*
            if(!Station() && !BTE(Stations.getStations().getBiclaIP())) {
                if (mightStopped) {
                    running = false;
                    sendRoute();
                }
            }else if (Station() && BTE(Stations.getStations().getBiclaIP())){
                if (running){
                    mightStopped=true;
                }else mightStarting=true;
            }else if(!Station() && BTE(Stations.getStations().getBiclaIP())){
                if (mightStarting) {
                    running = true;
                    startRoute();
                }
            }*/

            BTE(Stations.getStations().biclaIP);
        }

        public void setDistance(Location loc){
            if(locationsRoute.size()!=0){
                distance += locationsRoute.get(locationsRoute.size()-1).distanceTo(loc);
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

    private void startRoute() {
        //start saving the locations
    }

    private boolean BTE(String biclaIP) {
        //verificar se consegue encontrar nos peers o IP da bicla que reservou.
        ArrayList<Peers> atual = WifiApp.singleton.getConnectedPeersList();

        if(atual != null){
            for (Peers p : atual){
                if(p.getVirtualIP().equals(biclaIP)){
                    System.out.println("Encontrei a bicla!");
                    return true;
                }
            }
            return false;
        }else {
            System.out.println("No Peers!");
            return false;
        }
    }

    private boolean Station() {
        if (locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation1().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation1().getLongitude())
            || locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation2().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation2().getLongitude())
            || locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation3().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation3().getLongitude())){
            return true;
        } else return true;
    }

    private void revervabicla() { //TODO
    }

    public void sendRoute(){//TODO
        lastLocation=null;
        String latitude, longitude;
        String user=Client.getClient().getUsername();
        //InsertRouteCoordinates AssyngPut;

        //Create new route. Get route id to send locations
        //CreateNewRoute newRoute
        for (Location p :locationsRoute){
            latitude=String.valueOf(p.getLatitude());
            longitude=String.valueOf(p.getLongitude());

            //Enviar as coordenadas para o servidor.
            //AssyngPut= new InsertRouteCoordinates()

        }
    }
}
