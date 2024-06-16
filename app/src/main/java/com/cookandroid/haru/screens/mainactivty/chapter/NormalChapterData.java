package com.cookandroid.haru.screens.mainactivty.chapter;

import android.graphics.drawable.Drawable;

import com.cookandroid.haru.database.tableclass.userdata.UserData;

public class NormalChapterData extends ChapterData {
    public NormalChapterData(Drawable background, int imageID, UserData userData){
        super(background,imageID,userData);
        super.title = "UNIT " + userData.chapter;
        super.description = "UNIT" + userData.chapter +" 진행률";
        onGoing = total-studiedCount == 0 ? "학습완료" : "학습전";
        descriptionNumber = "(" + studiedCount + "/" + total + ")";
    }
}
