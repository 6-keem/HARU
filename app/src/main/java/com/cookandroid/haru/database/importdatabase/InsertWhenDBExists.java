package com.cookandroid.haru.database.importdatabase;

import android.content.Context;

import com.cookandroid.haru.database.WordsDatabase;

public class InsertWhenDBExists extends InsertIntoDB {
    public InsertWhenDBExists(Context context) {
        super(context);
    }

    @Override
    protected void insert() {
        WordsDatabase wordsDatabase = WordsDatabase.getInstanceWhenDBExistsOnSystemFolder(context);
    }
}
