package com.cookandroid.jlptvocabularyapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class}, version = 1 )
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase WordsDB = null;
    public abstract WordDao wordDao();

    public static WordsDatabase getInstance(Context context, String level){
        if(WordsDB == null) {
            WordsDB = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, ("jlpt_" + level + ".db"))
                    .allowMainThreadQueries()
                    .build();
            WordsDB.clearAllTables();
        }
        return WordsDB;
    }
    public static void destroyInstance(){ WordsDB = null; }
}
