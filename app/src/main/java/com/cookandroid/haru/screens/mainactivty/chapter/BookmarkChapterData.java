package com.cookandroid.haru.screens.mainactivty.chapter;

import android.graphics.drawable.Drawable;

import com.cookandroid.haru.database.tableclass.userdata.UserData;

public class BookmarkChapterData extends ChapterData{
    public BookmarkChapterData(Drawable background, int imageID, UserData userData, int bookmarkCount){
        super(background,imageID,userData);
        super.total = userData.studiedCount;
        super.studiedCount = bookmarkCount;
        super.title = "N" + userData.level +" 북마크";
        super.description = "N" + userData.level + " 북마크 단어 ";
        if(studiedCount > total) {
            descriptionNumber = "(" + studiedCount + "/" + studiedCount + ")";
            total = studiedCount;
        }
        else
            descriptionNumber = "(" + studiedCount + "/" + total + ")";
    }
}
