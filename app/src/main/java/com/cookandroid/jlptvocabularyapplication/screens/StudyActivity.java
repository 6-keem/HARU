package com.cookandroid.jlptvocabularyapplication.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    FragmentManager fragmentManager = getSupportFragmentManager();
    ArrayList<ChapterFragment> fragmentArrayList = new ArrayList<>();
    ArrayList<CardFragment> arrayList = new ArrayList<>();
    ViewPager2 viewPager = null;
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
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(this, arrayList);
        viewPager = (ViewPager2) findViewById(R.id.card_list);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(cardPagerAdapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer( (page, position) -> {
                float v = 1-Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
        });

        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setOnClickListener(view -> {
                viewPager.setCurrentItem(++currentPage, true);
            });
        }
    }

    void setCardItem(){
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