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

    @Query("SELECT SUM(total) " +
            "FROM userdata " +
            "WHERE chatper = 0 and level = 0")
    int getAllWordsCount();
    @Query("SELECT * FROM userdata " +
            "WHERE level LIKE :level" +
            " and chatper = :chapter")
    UserData getChapterData(String level, int chapter);

    @Query("SELECT * FROM userdata " +
            "WHERE level LIKE :level")
    List<UserData> getDatasEachLevel(String level);

    @Query("UPDATE userdata " +
            "SET studied_count = :studiedCount," +
            "count = :count " +
            "WHERE level LIKE :level and chatper = :chapter")
    void updateUserDate(int count, int studiedCount, String level, int chapter);

    @Query("UPDATE userdata " +
            "SET studied_count = studied_count + :studiedCount " +
            "WHERE level LIKE :level and chatper = :chapter")
    void updateChapterTestData(String level, int chapter,int studiedCount);
}
