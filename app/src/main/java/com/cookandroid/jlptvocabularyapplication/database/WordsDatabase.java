package com.cookandroid.jlptvocabularyapplication.database;

import android.content.Context;
import android.os.Handler;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;

@Database(entities = {Word.class, UserData.class, StudyData.class}, version = 1, exportSchema = false)
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase wordsDatabase = null;
    private static int wordCount = 0;
    private static boolean status = false;
    public abstract WordDao wordDao();
    public abstract UserDataDao userDataDao();
    public abstract StudyDataDao studyDataDao();
    public static WordsDatabase getInstance(Context context){
        if(wordsDatabase == null){
            wordsDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            WordsDatabase.class, ("jlpt.db"))
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
            wordsDatabase.inTransaction();
        }
        return wordsDatabase;
    }

    public static WordsDatabase getInstanceWhenDBExistsOnSystemFolder(Context context){
        status = true;
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
            wordsDatabase.inTransaction();
            status = true;
        }
        return wordsDatabase;
    }

    public static int getWordCount(){return wordCount;}
    public static void setWordCount(int count){ wordCount = count; }
    public static boolean getStatus(){return status;}
    public static void setStatus(boolean s){status = s;}
}
