package com.cookandroid.jlptvocabularyapplication.weather;

import com.cookandroid.jlptvocabularyapplication.weather.pojo.WeatherRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherService {
    @Headers("Accept: application/json")
    @GET("data/2.5/weather")
    Call<WeatherRoot> getData(@Query("lat") Double lat, @Query("lon") Double lon, @Query("APPID") String appKey);
}
