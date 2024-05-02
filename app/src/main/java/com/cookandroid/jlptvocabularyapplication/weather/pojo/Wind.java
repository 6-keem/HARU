package com.cookandroid.jlptvocabularyapplication.weather.pojo;

import com.google.gson.annotations.SerializedName;

   
public class Wind {

   @SerializedName("speed")
   double speed;

   @SerializedName("deg")
   int deg;

   @SerializedName("gust")
   double gust;


    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }
    
    public void setDeg(int deg) {
        this.deg = deg;
    }
    public int getDeg() {
        return deg;
    }
    
    public void setGust(double gust) {
        this.gust = gust;
    }
    public double getGust() {
        return gust;
    }
    
}