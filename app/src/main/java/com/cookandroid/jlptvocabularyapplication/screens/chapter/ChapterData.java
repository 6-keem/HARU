package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.graphics.drawable.Drawable;

public class ChapterData {
    private int imageID;
    private Drawable background;
    private int count, total;
    private String title, description;
    public ChapterData(Drawable background, int imageID, String title, String description, int count, int total){
        this.imageID = imageID;
        this.background = background;
        this.title = title;
        this.description = description;
        this.count = count;
        this.total = total;
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
