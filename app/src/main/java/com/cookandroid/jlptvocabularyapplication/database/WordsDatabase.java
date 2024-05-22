package com.cookandroid.jlptvocabularyapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;

@Database(entities = {Word.class, UserData.class}, version = 1, exportSchema = false)
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase wordsDatabase = null;
    public abstract WordDao wordDao();
    public abstract UserDataDao userDataDao();
    public static WordsDatabase getInstance(Context context){
        if(wordsDatabase == null){
            wordsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, ("jlpt.db"))
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            wordsDatabase.beginTransaction();
            wordsDatabase.endTransaction();
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
                    .allowMainThreadQueries()
                    .build();
            wordsDatabase.beginTransaction();
            wordsDatabase.endTransaction();
        }
        return wordsDatabase;
    }
}
