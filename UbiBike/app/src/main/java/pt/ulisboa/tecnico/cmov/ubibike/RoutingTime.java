package pt.ulisboa.tecnico.cmov.ubibike;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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

import pt.ulisboa.tecnico.cmov.ubibike.AsyncTask.CreateNewRoute;
import pt.ulisboa.tecnico.cmov.ubibike.AsyncTask.InsertRouteCoordinates;
import pt.ulisboa.tecnico.cmov.ubibike.AsyncTask.SetPoints;

public class RoutingTime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public LocationManager locationManager;
    public LocationUpdateListener listener;

    private ArrayList<Location> locationsRoute= new ArrayList<Location>();
    private ArrayList<Location> realRoute= new ArrayList<Location>();
    private double istLat=38.752694;
    private double istLong=-9.184699;
    private LatLng IST = new LatLng(istLat, istLong);

    CreateNewRoute route;

    private boolean running=false;
    private boolean mightStopped=false;
    private boolean mightStarting=false;
    private boolean firstTime=false;
    float distance=0;
    int count=0;
    int first=0;
    boolean endCareDone = true;

    String bikeid = Stations.getStations().getBiclaIP();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing_time);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationUpdateListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
          //Request Permissions

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            //Still granted?
            checkLocationPermission();
            checkLocationPermissionOutra();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);


        // Get a handle to the Map Fragment
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        mMap.setMyLocationEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(IST)                // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
            System.out.println("lat= "+latitude);
            System.out.println("lon= "+longitude);


            setDistance(location);
            locationsRoute.add(location);

            ////////////////////////////////////////////
            //Check if it is running or not

            if(!Station() && !BTE()) {
                System.out.println("caso Nenhum");

                if (mightStopped) {
                    running = false;
                    mightStarting=false;
                    System.out.println("fim da rota!");
                    if(endCareDone){
                        endCareDone=false;
                        endCare();
                    }
                }
            } else if (Station() && BTE()){
                System.out.println("caso Staton e BTE");

                if (running){
                    mightStopped=true;
                }else{
                    firstTime = true;
                    mightStarting=true;
                }
            } else if(!Station() && BTE()){
                System.out.println("caso BTE");
                if (mightStarting) {

                    running = true;
                }
            }

            if(running){
                System.out.println("Vai fazer um runningzinho!");
                if(firstTime){
                    firstTime=false;
                    initiateRoute();
                    sendRouteCoordinate(locationsRoute.get(locationsRoute.size()-2));
                    realRoute.add(locationsRoute.get(locationsRoute.size()-2));


                }else sendRouteCoordinate(location);

                setDistance(location);
                realRoute.add(location);


                // Instantiates a new Polyline object and adds points to define a rectangle
                PolylineOptions rectOptions = new PolylineOptions();
                for (Location p:realRoute) {
                    rectOptions.add(new LatLng(p.getLatitude(), p.getLongitude()));
                }
                // Get back the mutable Polyline
                Polyline polyline = mMap.addPolyline(rectOptions);
            }

            System.out.println("RUNNING= "+running+"\n"+"mightSTART = "+mightStarting+"\nmightSTOP= "+mightStopped);
            System.out.println("-------------------------------------------------------------------");

        }

        public void setDistance(Location loc){
            if(realRoute.size()!=0){
                distance += realRoute.get(realRoute.size()-1).distanceTo(loc); //distancia em metros
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

    private void endCare() {
        //Actualizar os pontos do utilizador.
        int aux = ((int) distance)/100; //parte inteira da distancia percorrida.
        int newPoint= Client.getClient().getPontos()+ aux; //1 ponto por cada 100 metros.
        System.out.println("pontos obtidos: "+aux);
        Client.getClient().setPontos(newPoint);
        SetPoints enviaPontos = new SetPoints(Client.getClient().getUsername(),newPoint);
        enviaPontos.execute();




    }

    private boolean BTE() {
        /*
        if(count>5/* && count <9){
            System.out.println("TEnho uma bicla perto de miiim!");
            return true;
        }else{
            System.out.println("não tenho bicla nenhuma!");
            return false;
        }*/

        if(Station()){
            if(first==0){
                first++;
                return false;
            }
            if(first==1){
                first++;
                return true;
            }
            if(first==2){
                first++;
                return true;
            }
        }
        if(first==2) {
            return true;
        }
        else return false;
    }

    private boolean Station() {
        if (locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation1().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation1().getLongitude())
            || locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation2().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation2().getLongitude())
            || locationsRoute.get(locationsRoute.size()-1).getLatitude()==(Stations.getStations().getStation3().getLatitude())
                && locationsRoute.get(locationsRoute.size()-1).getLongitude()==(Stations.getStations().getStation3().getLongitude())){
            System.out.println("estou numa station!");
            return true;
        } else{ return false;}
    }


    public void initiateRoute(){

        String user=Client.getClient().getUsername();

        route = new CreateNewRoute(user, bikeid);
        route.execute();
        sendRouteCoordinate(locationsRoute.get(locationsRoute.size()-1)); //Adicionar à rota a coordenada inicial onde se colocou o mightStart=true

    }

    public void sendRouteCoordinate(Location loc) {

        String lat = Location.convert(loc.getLatitude(), 0);
        String lon = Location.convert(loc.getLongitude(), 0);
        String routeid = Stations.getStations().getRouteID();


        InsertRouteCoordinates routeToSend = new InsertRouteCoordinates(lat, routeid , lon );
        routeToSend.execute();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    public boolean checkLocationPermissionOutra()
    {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
