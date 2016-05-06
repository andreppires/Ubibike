package pt.ulisboa.tecnico.cmov.ubibike;

import android.location.Location;

/**
 * Created by andreppires on 05-05-2016.
 */
public final class Stations {

    public Location station1=null;
    public Location station2=null;
    public Location station3=null;

    public Stations(){  //TODO criar um metodo no servidor que retorne as Cordenadas de cada estação.

        station1=new Location("Station1");
        station1.setAltitude(38.75322986);
        station1.setLongitude(-9.20676827);

        station2=new Location("Station2");
        station2.setAltitude(38.75077);
        station2.setLongitude(-9.19113);

        station3=new Location("Station3");
        station3.setAltitude(38.7601071);
        station3.setLongitude(-9.18283225);

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
}
