package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.Context;

import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.jsonparser.JsonReader;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.WordDao;

import java.util.List;

public class InsertWhenDBNotExists extends InsertIntoDB {
    public InsertWhenDBNotExists(Context context) {
        super(context);
    }

    @Override
    protected void run(){
        if(AssetChecker.isAssetExists(context.getAssets(),"jlpt.db"))
            WordsDatabase.getInstanceWhenDBFileExistsInAssetFolder(context);
        else
            super.run();
    }

    @Override
    protected void insert() {
        List<Word> words = new JsonReader(context.getAssets()).getWordsList();
        WordsDatabase wordsDatabase = WordsDatabase.getInstanceWhenDBFileNotExistsInAssetFolder(context);
        WordDao wordDao = wordsDatabase.wordDao();
        for(Word word : words)
            wordDao.insertWord(word);
    }
}
