package com.cookandroid.jlptvocabularyapplication.weather.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

   
public class WeatherRoot {

   @SerializedName("coord")
   Coord coord;

   @SerializedName("weather")
   List<Weather> weather;

   @SerializedName("base")
   String base;

   @SerializedName("main")
   Main main;

   @SerializedName("visibility")
   int visibility;

   @SerializedName("wind")
   Wind wind;

   @SerializedName("clouds")
   Clouds clouds;

   @SerializedName("dt")
   int dt;

   @SerializedName("sys")
   Sys sys;

   @SerializedName("timezone")
   int timezone;

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;

   @SerializedName("cod")
   int cod;


    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    public Coord getCoord() {
        return coord;
    }
    
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    
    public void setBase(String base) {
        this.base = base;
    }
    public String getBase() {
        return base;
    }
    
    public void setMain(Main main) {
        this.main = main;
    }
    public Main getMain() {
        return main;
    }
    
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
    public int getVisibility() {
        return visibility;
    }
    
    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public Wind getWind() {
        return wind;
    }
    
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
    public Clouds getClouds() {
        return clouds;
    }
    
    public void setDt(int dt) {
        this.dt = dt;
    }
    public int getDt() {
        return dt;
    }
    
    public void setSys(Sys sys) {
        this.sys = sys;
    }
    public Sys getSys() {
        return sys;
    }
    
    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }
    public int getTimezone() {
        return timezone;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setCod(int cod) {
        this.cod = cod;
    }
    public int getCod() {
        return cod;
    }
    
}