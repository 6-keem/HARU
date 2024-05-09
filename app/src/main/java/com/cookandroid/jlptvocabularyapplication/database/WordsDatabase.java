package com.cookandroid.jlptvocabularyapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.WordDao;

@Database(entities = {Word.class }, version = 1)
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase wordsDatabase = null;
    public abstract WordDao wordDao();

    public static WordsDatabase getInstance(Context context){
        if(wordsDatabase == null){
            wordsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, ("jlpt.db"))
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return wordsDatabase;
    }

    public static WordsDatabase getInstanceWhenDBExistsOnSystemFolder(Context context){
        return getInstance(context);
    }

    public static WordsDatabase getInstanceWhenDBFileNotExistsInAssetFolder(Context context){
        if(wordsDatabase == null) {
            getInstance(context);
            wordsDatabase.clearAllTables();
        }
        return wordsDatabase;
    }

    public static WordsDatabase getInstanceWhenDBFileExistsInAssetFolder(Context context){
        if(wordsDatabase == null) {
            wordsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, ("jlpt.db"))
                    .createFromAsset("jlpt.db")
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build();
            wordsDatabase.beginTransaction();
            wordsDatabase.endTransaction();
        }
        return wordsDatabase;
    }
}
