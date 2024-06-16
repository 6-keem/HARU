package com.cookandroid.haru.database.importdatabase;

import android.content.Context;

import java.io.File;

public class DatabaseChecker {
    public static boolean isDatabaseExists(Context context, String fileName){
        return new File(context.getDatabasePath(fileName).getAbsolutePath()).exists();
    }
}
