package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.Context;


/**
 * 앱 실행 초기 DB 생성 및 초기화 클래스
 * @author 6-keem
 */
public class InsertTableHandler {
    private InsertIntoDB insertIntoDB = null;
    private Context context = null;
    public InsertTableHandler(Context context){
        this.context = context;
        if(DatabaseChecker.isDatabaseExists(context,"jlpt.db"))
            insertIntoDB = new InsertWhenDBExists(context);
        else
            insertIntoDB = new InsertWhenDBNotExists(context);
    }
    public void insert(){ insertIntoDB.run();}
}
