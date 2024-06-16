package com.cookandroid.haru.database.converters;

import androidx.room.TypeConverter;

import com.cookandroid.haru.database.tableclass.word.Sentence;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SentenceConverter {
    @TypeConverter
    public String listToJson(List<Sentence> sentences){
        return new Gson().toJson(sentences);
    }
    @TypeConverter
    public List<Sentence> jsonToList(String sentences){
        Type listType = new TypeToken<List<Sentence>>(){}.getType();
        return new Gson().fromJson(sentences, listType);
    }
}
