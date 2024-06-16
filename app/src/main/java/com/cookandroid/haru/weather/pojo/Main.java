package com.cookandroid.haru.weather.pojo;

import com.google.gson.annotations.SerializedName;

   
public class Main {

   @SerializedName("temp")
   double temp;

   @SerializedName("feels_like")
   double feelsLike;

   @SerializedName("temp_min")
   double tempMin;

   @SerializedName("temp_max")
   double tempMax;

   @SerializedName("pressure")
   int pressure;

   @SerializedName("humidity")
   int humidity;

   @SerializedName("sea_level")
   int seaLevel;

   @SerializedName("grnd_level")
   int grndLevel;


    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getTemp() {
        return temp;
    }
    
    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }
    public double getFeelsLike() {
        return feelsLike;
    }
    
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }
    public double getTempMin() {
        return tempMin;
    }
    
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }
    public double getTempMax() {
        return tempMax;
    }
    
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    public int getPressure() {
        return pressure;
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public int getHumidity() {
        return humidity;
    }
    
    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }
    public int getSeaLevel() {
        return seaLevel;
    }
    
    public void setGrndLevel(int grndLevel) {
        this.grndLevel = grndLevel;
    }
    public int getGrndLevel() {
        return grndLevel;
    }
    
}