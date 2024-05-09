package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.Context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class InsertIntoDB {
    protected Context context;
    protected final Lock lock;
    public InsertIntoDB(Context context){
        this.context = context;
        this.lock = new ReentrantLock();
    }
    protected void run(){ insert(); }
    protected abstract void insert();
}
