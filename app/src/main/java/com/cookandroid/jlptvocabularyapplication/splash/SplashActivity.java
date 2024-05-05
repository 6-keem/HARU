package com.cookandroid.jlptvocabularyapplication.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;

import com.cookandroid.jlptvocabularyapplication.MainActivity;
import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.Word;
import com.cookandroid.jlptvocabularyapplication.database.WordDeserializer;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.insertintotable.InsertIntoTable;
import com.cookandroid.jlptvocabularyapplication.database.openjson.OpenJson;
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {
    private int delay = 0;
    private FusedLocationProviderClient fusedLocationProviderClient = null;

    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Thread(() ->{
            new InsertIntoTable(getApplicationContext()).insert();
        }).start();

//        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
//            delay = 5000;
//
//
//        new Handler().postDelayed(() ->{
//            getPermission();
//            LocationCoord.getInstance(fusedLocationProviderClient).getWeather();
//            new Handler().postDelayed(()-> {
//                Intent intent = new Intent(getBaseContext(), MainActivity.class).setAction("Splash Activity");
//                startActivity(intent);
//                finish();
//            }, 5000 - (delay/2));
//        },delay);
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
