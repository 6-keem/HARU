package com.cookandroid.jlptvocabularyapplication.database.importdatabase;

import android.content.res.AssetManager;

import java.io.IOException;

public class AssetChecker {

    public static boolean isAssetExists(AssetManager assetManager, String filePath) {
        try {
            String[] files = assetManager.list(""); // assets 폴더에 있는 파일 목록을 가져옴
            for (String file : files) {
                if (file.equals(filePath)) {
                    return true; // 파일이 존재하는 경우
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // 파일이 존재하지 않는 경우
    }
}