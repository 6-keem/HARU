package com.cookandroid.jlptvocabularyapplication.locationcoord;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cookandroid.jlptvocabularyapplication.weather.WeatherAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

public class LocationCoord {
    private static LocationCoord locationCoord = null;
    private static String iconString = null;
    private static FusedLocationProviderClient fusedLocationProviderClient = null;
    private Double lat, lon;
    private boolean status = false;
    public static LocationCoord getInstance(FusedLocationProviderClient fusedLocationProviderClient){
        if(locationCoord == null)
            locationCoord = new LocationCoord(fusedLocationProviderClient);
        return locationCoord;
    }
    public static LocationCoord getInstance(){
        if(locationCoord == null)
            return null;
        return locationCoord;
    }
    private LocationCoord(FusedLocationProviderClient f){
        fusedLocationProviderClient = f;
    }
    public void getWeather() {
        getLocationCoord();
        new Thread(() -> {
            while (!getStatus()) ;
            WeatherAPI weatherAPI = WeatherAPI.getInstance();
            weatherAPI.requestWeatherAPI(lat, lon);

            while (!WeatherAPI.getStatus()) ;
            iconString = WeatherAPI.getIconString();
            Log.d("User",iconString);
        }).start();
    }
    @SuppressLint("MissingPermission")
    private void getLocationCoord() {
        setStatus(false);
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
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
    public static String getIconString(){
        return iconString;
    }
    public boolean getStatus(){ return status; }
    private void setStatus(boolean s){ status = s; }
}
