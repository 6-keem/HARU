package com.cookandroid.jlptvocabularyapplication.database.jsonparser;

import android.content.res.AssetManager;
import android.util.Log;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonReader {
    private static AssetManager assetManager;
    private final Gson myGson = MyGson.getInstance();
    public JsonReader(AssetManager a){ assetManager = a; }

    public static String openFile(){
        String json = null;
        try{
            InputStream is = assetManager.open("jlpt.json");
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
        String json = openFile();
        if(json != null){
            Type listType = null;
            listType = new TypeToken<List<Word>>() {}.getType();
            return myGson.fromJson(json,listType);
        }
        return null;
    }
}
