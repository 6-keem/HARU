package com.cookandroid.jlptvocabularyapplication.database.insertintotable;

import android.content.Context;
import android.util.Log;

import com.cookandroid.jlptvocabularyapplication.database.Word;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.openjson.OpenJson;

import java.util.List;

// INSERT INTO TABLE -> when the database file is exist into the asset folers
public class InsertIntoTable {
    private Context context;
    public InsertIntoTable(Context context){
        this.context = context;
    }
    public void insert(){
        for(int i = 0 ; i < 5 ; i ++){
            int finalI = i;
            Log.d("USER",i + " - START");
            new Thread(() -> {
                boolean flag = DatabaseChecker.isDatabaseExists(context,"n"+(finalI+1) +".db");
                if(flag)
                    whenTheFileExist(finalI);
                else
                    whenTheFileNotExist(finalI);
            }).start();
            Log.d("USER",i + " - END");
        }
    }

    private void whenTheFileExist(int level){
        WordsDatabase wordsDatabase = WordsDatabase.createInstance(context, level, true);
        wordsDatabase.beginTransaction();
        wordsDatabase.endTransaction();
    }

    // clearAllTables 설정 해야함
    private void whenTheFileNotExist(int level){
        boolean isAssetExist = AssetChecker.isAssetExists(context.getAssets(), "n" + (level+1) + ".db");
        WordsDatabase wordsDatabase = null;
        if(isAssetExist){
            wordsDatabase = WordsDatabase.createInstance(context, level, false);
            wordsDatabase.beginTransaction();
            wordsDatabase.endTransaction();
        }
        else {
            List<Word> words = new OpenJson(context.getAssets(),level).getWordsList();
            wordsDatabase = WordsDatabase.createInstance(context, level, true);
            wordsDatabase.clearAllTables();
            for(Word word : words)
                wordsDatabase.wordDao().insertWord(word);
        }
    }
}
