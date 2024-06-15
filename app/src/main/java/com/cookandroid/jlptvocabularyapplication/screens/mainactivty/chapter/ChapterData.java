package com.cookandroid.jlptvocabularyapplication.screens.mainactivty.chapter;

import android.graphics.drawable.Drawable;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;

public class ChapterData {
    protected int imageID;
    protected Drawable background;
    protected int count, studiedCount, total;
    protected String title, description, descriptionNumber;
    protected String onGoing;
    public ChapterData(Drawable background, int imageID, UserData userData){
        this.imageID = imageID;
        this.background = background;
        this.studiedCount = userData.studiedCount;
        this.count = userData.count;
        this.total = userData.total;
    }

    public String getDescriptionNumber() {
        return descriptionNumber;
    }

    public String getOnGoing() {
        return onGoing;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStudiedCount() {
        return studiedCount;
    }

    public void setStudiedCount(int studiedCount) {
        this.studiedCount = studiedCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
