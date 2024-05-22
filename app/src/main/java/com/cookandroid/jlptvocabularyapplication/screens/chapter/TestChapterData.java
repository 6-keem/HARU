package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.graphics.drawable.Drawable;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;

public class TestChapterData extends ChapterData{
    public TestChapterData(Drawable background, int imageID, UserData userData){
        super(background,imageID,userData);
        if(userData.level == 0){
            super.description = "N1~N5 전체 진행률";
            super.title = "JLPT ALL";
        }
        else {
            super.title = "N" + userData.level + " 단어시험";
            super.description = "N" + userData.level + " 전체 진행률";
            int result = total - studiedCount;
            if(result == 0)
                onGoing = "학습완료";
            else if(result == total)
                onGoing = "학습전";
            else
                onGoing = "학습중";
        }
        descriptionNumber = "(" + studiedCount + "/" + total + ")";
    }

}
