package com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "studydata")
public class StudyData implements Serializable {

    public StudyData(String option, long time, long date, int level, int chapter, int wrong, int total){
        this.option = option;
        this.date = date;
        this.time = time;
        this.level = level;
        this.chapter = chapter;
        this.wrong = wrong;
        this.total = total;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    //test or study
    @ColumnInfo(name = "option")
    public String option;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "time")
    public long time;

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

    public long getDate() {
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