package com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "userdata")
public class UserData implements Serializable {
    // 회독 확인

    public UserData(int level, int chapter, int total){
        this.level = level;
        this.chapter = chapter;
        this.total = total;

        this.count = 0;
        this.studiedCount = 0;
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "count")
    public int count;

    // JLPT Level
    @ColumnInfo(name = "level")
    public int level;

    // check progress for each chapter
    @ColumnInfo(name = "chatper")
    public int chapter;

    @ColumnInfo(name = "total")
    public int total;

    @ColumnInfo(name = "studied_count")
    public int studiedCount;

    @ColumnInfo(name = "chapter_count")
    public int chapterCount;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStudiedCount() {
        return studiedCount;
    }

    public void setStudiedCount(int studiedCount) {
        this.studiedCount = studiedCount;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }
}