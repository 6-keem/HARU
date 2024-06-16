package com.cookandroid.haru.weather;

public interface WeatherAPICallBack {
    void onSuccess(String iconString);
    void onFailure(String message);
}
