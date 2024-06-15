package com.cookandroid.jlptvocabularyapplication.weather;

import android.util.Log;

import com.cookandroid.jlptvocabularyapplication.BuildConfig;
import com.cookandroid.jlptvocabularyapplication.weather.pojo.Weather;
import com.cookandroid.jlptvocabularyapplication.weather.pojo.WeatherRoot;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherAPI {
    private static String baseURL = "https://api.openweathermap.org/";
    private static WeatherAPI weatherAPI = null;
    private static boolean status = false;
    private static String iconString = null;
    private Double lat, lon;
    public static WeatherAPI getInstance(){
        if(weatherAPI == null)
            weatherAPI = new WeatherAPI();
        return weatherAPI;
    }
    public void requestWeatherAPI(Double lat, Double lon, WeatherAPICallBack weatherAPICallBack) {
        this.lat = lat;
        this.lon = lon;

        setStatus(false);
        setIconString(null);

        Retrofit retrofit = RetrofitManager.getInstance(baseURL);
        WeatherService service = retrofit.create(WeatherService.class);
        try {
            Response<WeatherRoot> response = service.getData(lat, lon, BuildConfig.weather_api_key).execute();
            if(response.isSuccessful()){
                WeatherRoot weatherRoot = response.body();
                assert weatherRoot != null;
                List<Weather> weathers = weatherRoot.getWeather();
                setIconString(weathers.get(0).getIcon());
                setStatus(true);
                weatherAPICallBack.onSuccess(weathers.get(0).getIcon());
            } else {
                weatherAPICallBack.onFailure("Weather API FAILED");
            }
        } catch (IOException exception){
            weatherAPICallBack.onFailure("Weather API I/O EXCEPTION");
        }

    }
    public static boolean getStatus(){ return status; }
    private static void setStatus(boolean s){ status = s; }
    public static String getIconString(){ return iconString; }
    private static void setIconString(String icon) { iconString = icon; }
}