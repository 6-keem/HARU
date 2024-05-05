package com.cookandroid.jlptvocabularyapplication.database.openjson;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.cookandroid.jlptvocabularyapplication.database.MyGson;
import com.cookandroid.jlptvocabularyapplication.database.Word;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class OpenJson {
    private Gson myGson = null;
    private static AssetManager assetManager;
    private int level = Integer.MAX_VALUE;
    public OpenJson(AssetManager a, int level){
        assetManager = a;
        this.myGson = MyGson.getInstance();
        this.level = level;
    }

    public static String openFile(int level){
        String json = null;
        try{
            InputStream is = assetManager.open("n" + (level+1) + ".json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null)
                sb.append(line);
            br.close();
            json = sb.toString();
        }catch (Exception e){
            Log.d("Exception", "I/O Exception");
        }
        return json;
    }

    public List<Word> getWordsList(){
        String json = openFile(level);
        if(json != null){
            Type listType = new TypeToken<List<Word>>(){}.getType();
            return myGson.fromJson(json, listType);
        }
        return null;
    }
}
