package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.Context;

import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;

public class InsertWhenDBExists extends InsertIntoDB {
    public InsertWhenDBExists(Context context) {
        super(context);
    }

    @Override
    protected void insert() {
        WordsDatabase wordsDatabase = WordsDatabase.getInstanceWhenDBExistsOnSystemFolder(context);
    }
}
