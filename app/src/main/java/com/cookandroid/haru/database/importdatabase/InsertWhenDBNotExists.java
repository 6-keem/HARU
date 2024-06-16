package com.cookandroid.haru.database.importdatabase;

import android.content.Context;

import com.cookandroid.haru.database.WordsDatabase;
import com.cookandroid.haru.database.jsonparser.JsonReader;
import com.cookandroid.haru.database.tableclass.word.Word;
import com.cookandroid.haru.database.tableclass.word.WordDao;

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
        int count = 0;
        for(Word word : words) {
            wordDao.insertWord(word);
            WordsDatabase.setWordCount(count++);
        }
        WordsDatabase.setStatus(true);
    }
}
