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
        wNmae = "Gil Sanovski";
        final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


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

                db.addRow(new Worker(wNmae,getTime(),getTime(),LatLangApp,LatLangApp));

                Log.d("LOCATION: ", "ALL: " + db.getAllRows());

            }
        });

        btOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.google.co.il"));
                startActivity(i);
                //Processes.this.finish();
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

    public String getTime () {
        Log.d("getTime: ", "getTime: ");
        // ...getting date automaticly...
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String timetocheck = df.format(Calendar.getInstance().getTime().toString());
        return timetocheck;
    }
}
