package com.cookandroid.haru.database.importdatabase;

import android.content.Context;

public abstract class InsertIntoDB {
    protected Context context;
    public InsertIntoDB(Context context){
        this.context = context;
    }
    protected void run(){ insert(); }
    protected abstract void insert();
}
