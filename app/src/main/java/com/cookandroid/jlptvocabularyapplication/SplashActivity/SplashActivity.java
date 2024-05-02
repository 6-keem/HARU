package com.cookandroid.jlptvocabularyapplication.SplashActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;

import com.cookandroid.jlptvocabularyapplication.MainActivity;
import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.weather.WeatherAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {
    private Double lat, lon;
    private boolean status = false;
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Thread(){
            public void run(){
                task();
            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class).setAction("Splash Activity");
                startActivity(intent);
                finish();
            }
            // Weather api 호출 대략 3초 걸림
        }, 3000);
    }

    private void task(){
        Log.d("User","Start");
        getGPSLocationCoord();

        while(!getStatus()){ }
        WeatherAPI weatherAPI = WeatherAPI.getInstance();
        weatherAPI.requestWeatherAPI(lat,lon);

        while (!WeatherAPI.getStatus()){ }
        String icon = WeatherAPI.getIconString();
        Log.d("USER",icon);
    }
    private void getGPSLocationCoord(){
        setStatus(false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) { return null; }
            @Override
            public boolean isCancellationRequested() { return false; }
        }).addOnSuccessListener(location -> {
            try {
                lat = location.getLatitude();
                lon = location.getLongitude();
                setStatus(true);
            } catch (NullPointerException ignored) {
                Log.d("Exception", "EXCEPTION IN REQUESTING LOCATION");
            }
        });
    }
    public boolean getStatus(){ return status; }
    private void setStatus(boolean s){ status = s; }
}
