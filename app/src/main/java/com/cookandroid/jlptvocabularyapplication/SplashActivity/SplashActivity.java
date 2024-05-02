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
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.cookandroid.jlptvocabularyapplication.weather.WeatherAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {
    private int delay = 0;
    private FusedLocationProviderClient fusedLocationProviderClient = null;

    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
            delay = 3000;


        new Handler().postDelayed(() ->{
            getPermission();
            LocationCoord.getInstance(fusedLocationProviderClient).getWeather();
            new Handler().postDelayed(()-> {
                Intent intent = new Intent(getBaseContext(), MainActivity.class).setAction("Splash Activity");
                startActivity(intent);
                finish();
            }, 3000 - delay);
        },delay);
    }
    private void getPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            while((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }
}
