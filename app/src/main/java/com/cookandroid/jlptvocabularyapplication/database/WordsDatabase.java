package com.cookandroid.jlptvocabularyapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Database(entities = {Word.class}, version = 1)
public abstract class WordsDatabase extends RoomDatabase {
    private static ConcurrentHashMap<String,WordsDatabase> databaseHashMap = new ConcurrentHashMap<>();
    public abstract WordDao wordDao();
    public static WordsDatabase createInstance(Context context, int level, boolean flag){
        String fileName = "n" + (level + 1);
        if(databaseHashMap.get(fileName) == null){
            WordsDatabase db;
            if(flag)
                db = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, (fileName+".db"))
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build();
            else
                db = Room.databaseBuilder(context.getApplicationContext(),
                                WordsDatabase.class, (fileName+".db"))
                    .createFromAsset(fileName + ".db")
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build();

            databaseHashMap.put(fileName,db);
            return db;
        }
        return databaseHashMap.get(fileName);
    }
    public static WordsDatabase getInstance(int level){
        if(databaseHashMap.get("n"+(level+1)) != null)
            return databaseHashMap.get("n"+(level+1));
        return null;
    }
    public static void clearAllTables(int level) {
        Objects.requireNonNull(databaseHashMap.get("n" + level)).clearAllTables();
    }
}
