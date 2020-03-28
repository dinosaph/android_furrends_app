package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AppInfoActivity extends AppCompatActivity {

    double latitude;
    double longitude;
    int MY_LOCATION_PERMISSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        TextView dataTxtView = findViewById(R.id.sensorData);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String dataTxt = new String();
        for (Sensor sensor : sensors) {
            dataTxt += sensor.getName() + " -> " + sensor.getPower() + "\n";
        }
        dataTxtView.setText(dataTxt);

        final Button locationBtn = findViewById(R.id.action_location);
        final TextView locationData = findViewById(R.id.locationData);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AppInfoActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("AppInfoActivity", "No ACCESS_FINE_LOCATION permission granted. Asking again.");
                    ActivityCompat.requestPermissions(AppInfoActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION);
                } else {
                    Log.d("AdoptionActivity", "ACCESS_FINE_LOCATION permission granted. Location can be generated now.");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locationListener);
                }
                locationData.setText("Longitude: " + longitude + "\nLatitude: " + latitude);
            }
        });
    }
}
