package com.cookandroid.jlptvocabularyapplication.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGson {
    public static Gson myGson = null;
    public static Gson getInstance(){
        if(myGson == null)
            myGson = new GsonBuilder()
                    .registerTypeAdapter(Word.class, new WordDeserializer())
                    .create();
        return myGson;
    }
}
