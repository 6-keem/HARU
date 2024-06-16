package com.cookandroid.haru.weather.pojo;

import com.google.gson.annotations.SerializedName;

   
public class Sys {

   @SerializedName("type")
   int type;

   @SerializedName("id")
   int id;

   @SerializedName("country")
   String country;

   @SerializedName("sunrise")
   int sunrise;

   @SerializedName("sunset")
   int sunset;


    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }
    
    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }
    public int getSunrise() {
        return sunrise;
    }
    
    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
    public int getSunset() {
        return sunset;
    }
    
}