package pt.ulisboa.tecnico.cmov.ubibike;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by andreppires on 05-05-2016.
 */
public final class Stations {

    public Location station1=null;
    public Location station2=null;
    public Location station3=null;

    public Stations(){  //TODO criar um metodo no servidor que retorne as Cordenadas de cada estação.

        station1=new Location("Station1");
        station1.setLatitude(38.75322833333333);
        station1.setLongitude(-9.206766666666665);

        station2=new Location("Station2");
        station2.setLatitude(38.75077);
        station2.setLongitude(-9.191128333333333);

        station3=new Location("Station3");
        station3.setLatitude(38.760106666666665);
        station3.setLongitude(-9.182831666666667);

        setBiclaIP("0.0.0.0:1");

    }

    public  Location getStation1() {
        return station1;
    }
    public  Location getStation2() {
        return station2;
    }
    public  Location getStation3() { return station3; }

    private static Stations stations=new Stations();

    public static Stations getStations() {
        return stations;
    }

    public String biclaIP=null;

    public String getBiclaIP() {
        return biclaIP;
    }

    public void setBiclaIP(String biclaIP) {
        this.biclaIP = biclaIP;
    }

    public ArrayList<String> getStationsList() {
        return stationsList;
    }

    public void addStationsList(String s) {
        this.stationsList.add(s);
    }

    ArrayList<String> stationsList = new ArrayList<String>();


    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    private String routeID="0";

}
