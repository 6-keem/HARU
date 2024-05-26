package com.cookandroid.jlptvocabularyapplication.screens.splashactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.importdatabase.InsertTableHandler;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.cookandroid.jlptvocabularyapplication.screens.mainactivty.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {
    private int delay = 0;
    private FusedLocationProviderClient fusedLocationProviderClient = null;
    private TextView textView;
    private ProgressBar progressBar;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        textView = findViewById(R.id.splash_textview);
        progressBar = findViewById(R.id.splash_progressbar);

        new Thread(() -> {
            new InsertTableHandler(getApplicationContext()).insert();
        }).start();

        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
            delay = 3000;

        new Handler().postDelayed(() -> {
            getPermission();
            LocationCoord.getInstance(fusedLocationProviderClient).getWeather();
            initializeWordDatabase();
        }, delay);
    }

    private void getPermission() {
        // 로그인 화면 추가하여 무한루프 -> 앱 멈춤 제거
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initializeWordDatabase() {
        if (WordsDatabase.getStatus()) {
            moveToMainActivity();
            return;
        }

        mainHandler.post(() -> {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setMax(4818);
        });

        new Thread(() -> {
            while (!WordsDatabase.getStatus()) {
                int wordCount = WordsDatabase.getWordCount();
                mainHandler.post(() -> progressBar.setProgress(wordCount));
                try {
                    Thread.sleep(100); // 업데이트 간의 짧은 지연 시간
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mainHandler.post(this::moveToMainActivity);
        }).start();
    }

    private void moveToMainActivity() {
        initializeUserData();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class).setAction("Splash Activity");
        startActivity(intent);
        finish();
    }

    private void initializeUserData() {
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();

        List<UserData> userDatas = userDataDao.getAllUserData();
        int totalCount = wordDao.getWordsCount("_", 0);
        if (userDatas.size() == 0) {
            userDataDao.insertUserData(new UserData(0, 0, totalCount));
            for (int i = 1; i < 6; i++) {
                int total = wordDao.getWordsCount(Integer.toString(i), 0);
                int factor = (total / 8) + 1;
                UserData userData = new UserData(i, 0, total);
                userData.chapterCount = 8;
                userDataDao.insertUserData(userData);
                for (int j = 1; j < 8; j++)
                    userDataDao.insertUserData(new UserData(i, j, factor));
                userDataDao.insertUserData(new UserData(i, 8, total % factor));
            }
        }
    }
}
