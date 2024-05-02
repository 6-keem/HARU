package com.cookandroid.jlptvocabularyapplication.weather.pojo;


import com.google.gson.annotations.SerializedName;

   
public class Clouds {

   @SerializedName("all")
   int all;


    public void setAll(int all) {
        this.all = all;
    }
    public int getAll() {
        return all;
    }
    
}