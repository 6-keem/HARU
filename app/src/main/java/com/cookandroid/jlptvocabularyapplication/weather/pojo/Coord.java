package com.cookandroid.jlptvocabularyapplication.weather.pojo;


import com.google.gson.annotations.SerializedName;

   
public class Coord {

   @SerializedName("lon")
   double lon;

   @SerializedName("lat")
   double lat;


    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getLon() {
        return lon;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLat() {
        return lat;
    }
    
}