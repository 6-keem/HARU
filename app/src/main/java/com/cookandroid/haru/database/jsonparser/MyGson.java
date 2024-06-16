package com.cookandroid.haru.database.jsonparser;

import com.cookandroid.haru.database.converters.WordDeserializer;
import com.cookandroid.haru.database.tableclass.word.Word;
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
