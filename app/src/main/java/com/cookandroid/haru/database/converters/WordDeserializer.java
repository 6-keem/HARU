package com.cookandroid.haru.database.converters;

import com.cookandroid.haru.database.tableclass.word.Sentence;
import com.cookandroid.haru.database.tableclass.word.Word;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WordDeserializer implements JsonDeserializer<Word> {
    @Override
    public Word deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Word word = new Gson().fromJson(json, Word.class);
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray wordArray = (JsonArray) jsonObject.get("word_meaning");
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
                arrayList.add(new Sentence(obj.get("jp").getAsString(),obj.get("kr").getAsString()));
            }
            word.setSentences(arrayList);
        }
        return word;
    }
}