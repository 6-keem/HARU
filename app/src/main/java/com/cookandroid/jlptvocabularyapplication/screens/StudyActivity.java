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
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.CardPagerAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.MyTextToSpeech;

import java.util.ArrayList;


@SuppressLint("SetTextI18n")
public abstract class StudyActivity extends AppCompatActivity {
    protected ArrayList<CardFragment> arrayList = new ArrayList<>();
    protected CardPagerAdapter cardPagerAdapter = null;
    protected ViewPager2 viewPager = null;
    protected ProgressBar progressBar = null;
    protected int level, position, wordEnd, retryCount, currentPage = 0;
    protected TextView currentCount = null;
    protected Chronometer chronometer = null;
    abstract protected void setCardItem();
    abstract protected void onExit(int factor);
    abstract protected void setScrollEvent();
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

        transformer.addTransformer( (page, position) -> {
                float v = 1-Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
        });
        setToolBar();
        setWidgets();
        setScrollEvent();
    }

    private void setWidgets() {
        // TODO: 2024-05-17 DB 정보 가져와서 초기화
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgress(currentPage+1);
        progressBar.setMax(wordEnd);

        currentCount = (TextView) findViewById(R.id.count);
        TextView total = (TextView)findViewById(R.id.total_count);
        currentCount.setText(Integer.toString(currentPage+1));
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}