package com.cookandroid.jlptvocabularyapplication.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WordMeaningConverter {
    @TypeConverter
    public String listToJson(List<String> wordMeaing){
        return new Gson().toJson(wordMeaing);
    }
    @TypeConverter
    public List<String> jsonToList(String wordMeaing){
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(wordMeaing, listType);
    }
}
