package com.cookandroid.jlptvocabularyapplication.database.tableclass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDataDao {
    @Insert
    public void insertUserData(UserData userData);

    @Transaction
    @Query("SELECT * FROM userdata")
    LiveData<List<UserData>> getAllUserData();

    @Query("SELECT date FROM userdata " +
            "ORDER BY date DESC")
    List<Date> getAllDate();

    @Query("SELECT * FROM userdata " +
            "WHERE level = :level" +
            " and chatper = :chapter")
    List<UserData> getChapterData(int level, int chapter);
}
