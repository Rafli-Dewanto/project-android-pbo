package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.tapadoo.alerter.Alerter;

import java.util.List;
import java.util.Locale;

public class GPS extends AppCompatActivity implements LocationListener {
    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;
    Button getLocationButton;

    // Initializing other items
    // from layout file
    TextView latitudeTextView, longitudeTextView;
    ImageView backArrowButton;
    int PERMISSION_ID = 44;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        latitudeTextView = findViewById(R.id.tv_latitude);
        longitudeTextView = findViewById(R.id.tv_longitude);
        backArrowButton = findViewById(R.id.btn_back);

        latitudeTextView.setVisibility(View.GONE);
        longitudeTextView.setVisibility(View.GONE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationButton = findViewById(R.id.btn_get_location);

        if (ContextCompat.checkSelfPermission(GPS.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GPS.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        getLocationButton.setOnClickListener(v -> {
            isLoading();
            getLocation();
        });

        backArrowButton.setOnClickListener(v -> {
            Intent i = new Intent(GPS.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, GPS.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void isLoading() {
        if (latitudeTextView.getVisibility() == View.GONE && longitudeTextView.getVisibility() == View.GONE) {
            getLocationButton.setEnabled(false);
            Alerter.create(GPS.this)
                    .setTitle("Getting Location...")
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .enableProgress(true)
                    .show();
        } else {
            getLocationButton.setEnabled(true);
            Alerter.clearCurrent(GPS.this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        latitudeTextView.setText(String.format("Current Latitude: %s", latitude));
        longitudeTextView.setText(String.format("Current Longitude: %s", longitude));
        getLocationButton.setEnabled(true);

        new Handler().postDelayed(() -> {
            longitudeTextView.setVisibility(View.VISIBLE);
            latitudeTextView.setVisibility(View.VISIBLE);
            isLoading();
            Alerter.create(GPS.this)
                    .setTitle("Success")
                    .setText("Location acquired")
                    .setDuration(6000)
                    .setBackgroundColorRes(R.color.success)
                    .show();
        }, 2000);


        // Currently not working (grpc error)
        try {
            Geocoder geocoder = new Geocoder(GPS.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
            Log.i("gps", "berhasil get address");
        } catch (Exception e) {
            Log.e("gps", e.getMessage());
        }
    }
}