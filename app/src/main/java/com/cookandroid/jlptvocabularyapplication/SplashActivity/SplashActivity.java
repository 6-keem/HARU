package com.cookandroid.jlptvocabularyapplication.SplashActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.cookandroid.jlptvocabularyapplication.database.Sentence;
import com.cookandroid.jlptvocabularyapplication.database.Word;
import com.cookandroid.jlptvocabularyapplication.database.WordDeserializer;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.cookandroid.jlptvocabularyapplication.weather.WeatherAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {
    private int delay = 0;
    private FusedLocationProviderClient fusedLocationProviderClient = null;

    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        new Thread(() -> {
            task(this.getApplicationContext());
        }).start();

//        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
//            delay = 3000;
//
//
//        new Handler().postDelayed(() ->{
//            getPermission();
//            LocationCoord.getInstance(fusedLocationProviderClient).getWeather();
//            new Handler().postDelayed(()-> {
//                Intent intent = new Intent(getBaseContext(), MainActivity.class).setAction("Splash Activity");
//                startActivity(intent);
//                finish();
//            }, 3000 - delay);
//        },delay);
    }
    private void task(Context context){
        String json = null;
        try{
            InputStream is = context.getAssets().open("n5.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null)
                sb.append(line);
            br.close();
            json = sb.toString();
        }catch (Exception e){
            Log.d("Exception", "I/O Exception");
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Word.class, new WordDeserializer())
                .create();
        Type listType = new TypeToken<List<Word>>(){}.getType();
        // TODO: 2024-05-04 이 사이에서 wd_meaing, sentences 정보 소실
        List<Word> words = gson.fromJson(json, listType);
        WordsDatabase wordsDatabase = WordsDatabase.getInstance(this,"n5");
        for(Word word : words){
            wordsDatabase.wordDao().insertWord(word);
        }
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
