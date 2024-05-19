package com.cookandroid.jlptvocabularyapplication.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.CardPagerAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.MyTextToSpeech;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {
    ArrayList<CardFragment> arrayList = new ArrayList<>();
    private CardPagerAdapter cardPagerAdapter = null;
    private ViewPager2 viewPager = null;
    private ProgressBar progressBar = null;
    private int currentPage = 1;
    private int level, position, wordEnd, retryCount;
    private TextView currentCount = null;
    private Chronometer chronometer = null;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);

        MyTextToSpeech.getInstance(getApplicationContext());
        Intent intent = getIntent();
        try {
            level = intent.getExtras().getInt("level");
            position = intent.getExtras().getInt("position");
        } catch (NullPointerException ignore) { }
        setCardItem();
        cardPagerAdapter = new CardPagerAdapter(this, arrayList);
        viewPager = (ViewPager2) findViewById(R.id.card_list);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(cardPagerAdapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));

        setToolBar();
        setWidgets();

        transformer.addTransformer( (page, position) -> {
                float v = 1-Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
        });

        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setSkipButtonOnClickListener(view -> {
                if(currentPage == wordEnd){
                    // TODO: 2024-05-18 팝업 띄우고 종료하기
                    chronometer.stop();
                    onExit(1);
                    finish();
                }
                viewPager.setCurrentItem(++currentPage, true);
                progressBar.setProgress(currentPage);
                currentCount.setText(Integer.toString(currentPage));
            });
        }
    }

    private void setWidgets() {
        // TODO: 2024-05-17 DB 정보 가져와서 초기화
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgress(currentPage);
        progressBar.setMax(wordEnd);

        currentCount = (TextView) findViewById(R.id.count);
        TextView total = (TextView)findViewById(R.id.total_count);
        currentCount.setText(Integer.toString(currentPage));
        total.setText("/" + wordEnd);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void setToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        ImageButton imageButton = (ImageButton) findViewById(R.id.back_arrow);
        toolbarTitle.setText("N" + level + " UNIT " + position);
        imageButton.setOnClickListener(v -> {
           finish();
        });
        setSupportActionBar(toolbar);
    }

    private void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        UserData userData = userDataDao.getChapterData(level, position);

        TextView textView = (TextView)findViewById(R.id.retry_count);
        textView.setText(Integer.toString(userData.count + 1));

        int beginID = wordDao.getLevelsFirstWordID(level);
        int begin = beginID + (userData.total * (position - 1));
        int end = begin + userData.total;
        wordEnd = userData.total - 1;
        retryCount = userData.count;

        List<Word> words = wordDao.getWords(level, begin, end);
        for (Word word : words){
            arrayList.add(new CardFragment(word));
        }
    }

    private void onExit(int factor){
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        userDataDao.updateUserDate(retryCount + factor, currentPage - 1, level, position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}