package com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;

import java.util.List;

@Dao
public interface StudyDataDao {
    @Insert
    public void insertStudyData(StudyData studyData);

    @Transaction
    @Query("SELECT * FROM studydata")
    List<StudyData> getAllStudyData();
}
