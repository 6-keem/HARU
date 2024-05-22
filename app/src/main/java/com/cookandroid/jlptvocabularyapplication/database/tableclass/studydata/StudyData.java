package com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "studydata")
public class StudyData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    //test or study
    @ColumnInfo(name = "option")
    public String option;

    @ColumnInfo(name = "date")
    public String date;

    // JLPT Level
    @ColumnInfo(name = "level")
    public int level;

    // check progress for each chapter
    @ColumnInfo(name = "chatper")
    public int chapter;

    @ColumnInfo(name = "wrong")
    public int wrong;

    @ColumnInfo(name = "total")
    public int total;

    public int getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public String getDate() {
        return date;
    }

    public int getLevel() {
        return level;
    }

    public int getChapter() {
        return chapter;
    }

    public int getWrong() {
        return wrong;
    }

    public int getTotal() {
        return total;
    }
}