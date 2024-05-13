package com.cookandroid.jlptvocabularyapplication.database.tableclass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


/**
 * 정보 없으면 progress 0
 *
 */
@Entity(tableName = "userdata")
public class UserData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "case_id")
    public long caseID;

    // 회독 확인
    @ColumnInfo(name = "count")
    public long count;

    // Check for last study day
    @ColumnInfo(name = "date")
    public Date date = null;

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

    public long getCaseID() {
        return caseID;
    }

    public void setCaseID(long caseID) {
        this.caseID = caseID;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}