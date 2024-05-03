package com.cookandroid.jlptvocabularyapplication.database;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WordDeserializer implements JsonDeserializer<Word> {
    @Override
    public Word deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Word word = new Gson().fromJson(json, Word.class);
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray wordArray = (JsonArray) jsonObject.get("wd_meaning");
        JsonArray sentenceArray = (JsonArray) jsonObject.get("sentences");

        word.setWordMeaning(null);
        word.setSentences(null);

        if (word.getWordMeaning() == null && wordArray != null) {
            ArrayList<String> arrayList = new ArrayList<>();
            for(int i = 0 ; i < wordArray.size() ; i++)
                arrayList.add(wordArray.get(i).getAsString());
            word.setWordMeaning(arrayList);
        }
        if (word.getSentences() == null && sentenceArray != null) {
            ArrayList<Sentence> arrayList = new ArrayList<>();
            for(int i = 0 ; i < sentenceArray.size() ; i++){
                JsonObject obj = sentenceArray.get(i).getAsJsonObject();
                arrayList.add(new Sentence(obj.get("sentence").getAsString(),obj.get("st_meaning").getAsString()));
            }
            word.setSentences(arrayList);
        }

        return word;
    }
}