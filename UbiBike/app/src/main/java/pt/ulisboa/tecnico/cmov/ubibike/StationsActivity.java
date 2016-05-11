package pt.ulisboa.tecnico.cmov.ubibike;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.androidmapsextensions.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class StationsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private double istLat=38.752694;
    private double istLong=-9.184699;
    private LatLng IST = new LatLng(istLat, istLong);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getExtendedMapAsync(this);

        final Button buttonReservar = (Button) findViewById(R.id.reservar);
        final Button buttonReservar2 = (Button) findViewById(R.id.reservar2);
        final Button buttonReservar3 = (Button) findViewById(R.id.reservar3);

        buttonReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationsActivity.this, BikesStation1.class);
                startActivity(intent);
            }
        });

        buttonReservar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationsActivity.this, BikesStation2.class);
                startActivity(intent);
            }
        });

        buttonReservar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationsActivity.this, BikesStation3.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(IST)                // Sets the center of the map to Mountain View
                .zoom(14)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        setUpMap();
    }

    private void setUpMap() {

        Marker station1marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(38.75322986, -9.20676827))
                .title("Station 1"));

        Marker station2marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(38.75077, -9.19113))
                .title("Station 2"));

        Marker station3marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(38.760197, -9.18499773))
                .title("Station 3"));

        station1marker.setVisible(false);
        station2marker.setVisible(false);
        station3marker.setVisible(false);


        if(Stations.getStations().getStationsList().contains("Store1"))
            station1marker.setVisible(true);
        if(Stations.getStations().getStationsList().contains("Store2"))
            station2marker.setVisible(true);
        if(Stations.getStations().getStationsList().contains("Store3"))
            station3marker.setVisible(true);

        mMap.setOnMarkerClickListener(this);

        station1marker.setData(station1MarkerClickListener);
        station2marker.setData(station2MarkerClickListener);
        station3marker.setData(station3MarkerClickListener);

    }

    private GoogleMap.OnMarkerClickListener station1MarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            final Button buttonReservar = (Button) findViewById(R.id.reservar);
            final Button buttonReservar2 = (Button) findViewById(R.id.reservar2);
            final Button buttonReservar3 = (Button) findViewById(R.id.reservar3);
            buttonReservar.setVisibility(View.VISIBLE);
            buttonReservar2.setVisibility(View.GONE);
            buttonReservar3.setVisibility(View.GONE);
            return true;
        }
    };

    private GoogleMap.OnMarkerClickListener station2MarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            final Button buttonReservar = (Button) findViewById(R.id.reservar);
            final Button buttonReservar2 = (Button) findViewById(R.id.reservar2);
            final Button buttonReservar3 = (Button) findViewById(R.id.reservar3);

            buttonReservar.setVisibility(View.GONE);
            buttonReservar2.setVisibility(View.VISIBLE);
            buttonReservar3.setVisibility(View.GONE);
            return true;
        }
    };

    private GoogleMap.OnMarkerClickListener station3MarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            final Button buttonReservar = (Button) findViewById(R.id.reservar);
            final Button buttonReservar2 = (Button) findViewById(R.id.reservar2);
            final Button buttonReservar3 = (Button) findViewById(R.id.reservar3);

            buttonReservar.setVisibility(View.GONE);
            buttonReservar2.setVisibility(View.GONE);
            buttonReservar3.setVisibility(View.VISIBLE);
            return true;
        }
    };

    @Override
    public boolean onMarkerClick(Marker marker) {
        GoogleMap.OnMarkerClickListener listener = marker.getData();
        return listener.onMarkerClick(marker);
    }

}
