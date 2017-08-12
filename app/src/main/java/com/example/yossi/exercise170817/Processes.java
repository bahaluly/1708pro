package com.example.yossi.exercise170817;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.yossi.exercise170817.MapsActivity.myAppLocation;

public class Processes extends AppCompatActivity {

    TextView tvName, tvMap, TvWeb, tvCam;
    Button btIn, btOut;
    TextClock textClock;
    String wNmae;
    private static final int CAMERA_REQUEST = 165;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes);

        tvName = (TextView) findViewById(R.id.tvName);
        tvMap = (TextView) findViewById(R.id.tvMap);
        TvWeb = (TextView) findViewById(R.id.TvWeb);
        tvCam = (TextView) findViewById(R.id.tvCam);
        btIn = (Button) findViewById(R.id.btIn);
        btOut = (Button) findViewById(R.id.btOut);
        textClock = (TextClock) findViewById(R.id.textClock);
        //
        wNmae = "Gil Snovsky";
        final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        db = new DBHandler(this);


        btIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                double longitudeApp, longitudeReal;
                double latitudeApp, latitudeReal;
                if (ActivityCompat.checkSelfPermission(Processes.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Processes.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Log.d("LOCATION: ", "myAppLocation: " + myAppLocation);

                String LatLangApp = String.valueOf(latitudeApp)+","+String.valueOf(longitudeApp);
                Log.d("LOCATION: ", "myAppLocation: " + LatLangApp);

                /*DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Log.d("getTime: ", "getTime: 2 " + df);
                String timetocheck = df.format(Calendar.getInstance().getTime());
                Log.d("getTime: ", "getTime: 2 " + timetocheck);*/

                Log.d("LOCATION: ", "ALL: " + "name: "+wNmae+" time: "+getNewTime()+" place: "+LatLangApp);
                db.addRow(new Worker(wNmae,getNewTime(),getNewTime(),LatLangApp,LatLangApp));

                getAllToLog();
            }
        });

        btOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String endOfDay = getNewTime();
                Log.d("getTime: ", "getTime: 3" + endOfDay);
                db.updateEndOfDay(endOfDay);
                getAllToLog();
                Processes.this.finish();
            }
        });


        tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Processes.this, MapsActivity.class);
                Processes.this.startActivity(myIntent);
                //Processes.this.finish();
            }
        });

        TvWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.google.co.il"));
                startActivity(i);
                //Processes.this.finish();
            }
        });

        tvCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                //Log.i("Image is: ", "Camera by button");
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST)
        {
            Log.i("Image is: ", "Camera by image");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            MediaStore.Images.Media.insertImage(getContentResolver(), photo, "aaa" , "bbb");

        }
    }


    public String getNewTime() {
        Log.d("getTime: ", "getTime: 1");
        // ...getting date automaticly...
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String timetocheck = df.format(Calendar.getInstance().getTime());
        Log.d("getTime: ", "getTime: 2");
        return timetocheck;
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
