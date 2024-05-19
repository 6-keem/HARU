package com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata;

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
    List<UserData> getAllUserData();

    @Query("SELECT * FROM userdata " +
            "WHERE level = :level" +
            " and chatper = :chapter")
    UserData getChapterData(int level, int chapter);

    @Query("SELECT * FROM userdata " +
            "WHERE level = :level")
    List<UserData> getDatasEachLevel(int level);

    @Query("UPDATE userdata " +
            "SET studied_count = :studiedCount," +
            "count = :count " +
            "WHERE level = :level and chatper = :chapter")
    void updateUserDate(int count, int studiedCount, int level, int chapter);
}
