package com.cookandroid.jlptvocabularyapplication.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardData;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity {
    ArrayList<CardFragment> arrayList = new ArrayList<>();
    private CardPagerAdapter cardPagerAdapter = null;
    private ViewPager2 viewPager = null;
    private ProgressBar progressBar = null;
    private int currentPage = 0;
    private int level, factor, position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);

        Intent intent = getIntent();
        try {
            level = intent.getExtras().getInt("level");
            factor = intent.getExtras().getInt("factor");
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

        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
//        toolbarTitle.setText("JLPT " + level);
        // TODO: 2024-05-17 DB 정보 가져와서 초기화
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgress(0);
        progressBar.setMax(arrayList.size());

        TextView count = (TextView) findViewById(R.id.count);
        TextView total = (TextView)findViewById(R.id.total_count);
        count.setText(Integer.toString(0));
        total.setText("/" + arrayList.size());

        transformer.addTransformer( (page, position) -> {
                float v = 1-Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
        });

        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setSkipButtonOnClickListener(view -> {
                viewPager.setCurrentItem(++currentPage, true);
                progressBar.setProgress(currentPage);
                count.setText(Integer.toString(currentPage));
            });
        }
    }

    private void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();

        int beginID = wordDao.getLevelsFirstWordID(level);
        int begin = beginID + (factor * (position - 1));
        int end = begin + factor;

        List<Word> words = wordDao.getWords(level, begin, end);
        for (Word word : words){
            arrayList.add(new CardFragment(word));
        }
    }

}