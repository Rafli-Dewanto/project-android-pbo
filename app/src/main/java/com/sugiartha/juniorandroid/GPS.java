package com.sugiartha.juniorandroid;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPS extends AppCompatActivity {
    private FusedLocationProviderClient client;
    @SuppressLint("MissingPermission")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(GPS.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    System.out.println("Cek Permission");
                    return;
                }
                client.getLastLocation().addOnSuccessListener(GPS.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            TextView textView = findViewById(R.id.location);
                            textView.setText(location.toString());
                            System.out.print("Cek Lokasi: "+location.toString());

                            Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                            // Display in Toast
                            Toast.makeText(GPS.this,
                                    "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        // GET CURRENT LOCATION
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    // Display in Toast
                    Toast.makeText(GPS.this,
                            "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        System.out.println("Cek Request Permission");
    }
}