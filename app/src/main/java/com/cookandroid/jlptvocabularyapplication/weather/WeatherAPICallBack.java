package com.cookandroid.jlptvocabularyapplication.weather;

public interface WeatherAPICallBack {
    void onSuccess(String iconString);
    void onFailure(String message);
}
