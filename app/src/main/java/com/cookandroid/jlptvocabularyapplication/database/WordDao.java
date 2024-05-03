package com.cookandroid.jlptvocabularyapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    public void insertWord(Word word);

    @Transaction
    @Query("SELECT * FROM word")
    LiveData<List<Word>> getAllWords();
}
