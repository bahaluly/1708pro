package com.example.yossi.exercise170817;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback, LocationListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private static GoogleMap mMap;
    private static Location lastLocation;
    public static Location myAppLocation, myRealLocation;
    public double longitudeApp, longitudeWorker;
    public double latitudeApp, latitudeWorker;
    public SupportMapFragment mapFragment;
    DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        db = new DBHandler(this);
        mMap = googleMap;
        Log.d("LOCATION: ", "myAppLocation:111 ");
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("LOCATION: ", "myAppLocation:122 ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myAppLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitudeApp = myAppLocation.getLongitude();
        latitudeApp = myAppLocation.getLatitude();
        Log.d("LOCATION: ", "myAppLocation: 222" + myAppLocation);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
        //32.173049, 34.848616 latitudeApp, longitudeApp
        LatLng NOWLOCATION = new LatLng(latitudeApp, longitudeApp);
        Marker nowlocation = mMap.addMarker(new MarkerOptions()
                .position(NOWLOCATION)
                .title("You are hear now").draggable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(NOWLOCATION));
        mMap.animateCamera(zoom);

        nowlocation.showInfoWindow(); //, Marker nowlocation =

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                latitudeWorker = arg0.getPosition().latitude;
                longitudeWorker = arg0.getPosition().longitude;
                String LatLangUser = String.valueOf(latitudeWorker)+","+String.valueOf(longitudeWorker);
                Log.i("testergeio...",""+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);

                Worker test = db.getLastId();
                if (test.getId()>0) {
                    Log.d("getWorker: ", "onMarkerDragEnd: 2  " + test.getId()+ "  "+
                            test.getApplocation()+ "  "+ test.getUserlocation());
                    if (test.getApplocation().equals(test.getUserlocation())){
                        db.updateMoveByUser(LatLangUser, test.getId());
                        getAllToLog();
                        //MapsActivity.this.finish();
                    }else {
                        Toast.makeText(MapsActivity.this, "No change in map!!", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MapsActivity.this, "You must first open the day, so you can close it!!", Toast.LENGTH_LONG).show();
                    getAllToLog();
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });




    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        latitudeWorker = location.getLatitude();
        longitudeWorker = location.getLongitude() ;

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getAllToLog(){
        //first we will get all DB to screen !!
        List<Worker> worker = db.getAllRows();
        for (Worker users : worker) {
            String log = "Id: " + users.getId() + " ,Name: " + users.getName()
                    + " intime: " + users.getIntime() + " outtime: " + users.getOuttime()
                    + " apploc: " + users.getApplocation() + " userloc: " + users.getUserlocation();
            // Writing DB  to log
            Log.d("users: : ", log);}
    }

}
