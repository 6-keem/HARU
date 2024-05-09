package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.Context;

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
