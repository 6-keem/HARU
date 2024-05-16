package com.cookandroid.jlptvocabularyapplication.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.WordDao;
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterFragment;
import com.cookandroid.jlptvocabularyapplication.screens.level.LevelRecyclerViewAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardData;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ArrayList<ChapterFragment> fragmentArrayList = new ArrayList<>();
    ArrayList<CardFragment> arrayList = new ArrayList<>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_header);
        RecyclerView recyclerView = (RecyclerView)  findViewById(R.id.level_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        ArrayList<String> levelItems = setLevelItem();
        LevelRecyclerViewAdapter levelRecyclerViewAdapter = new LevelRecyclerViewAdapter(this, levelItems);
        recyclerView.setAdapter(levelRecyclerViewAdapter);

        setChapterFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, fragmentArrayList.get(0));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    ArrayList<String> setLevelItem(){
        ArrayList<String> levelItems = new ArrayList<>();
        levelItems.add("ALL");
        levelItems.add("N5");
        levelItems.add("N4");
        levelItems.add("N3");
        levelItems.add("N2");
        levelItems.add("N1");
        return levelItems;
    }

    private void setChapterFragments(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        fragmentArrayList.add(new ChapterFragment(getApplicationContext(),
                0, wordDao.getWordsCount(0,0)));

        for(int i = 5 ; i >= 1 ; i--)
            fragmentArrayList.add(new ChapterFragment(getApplicationContext(), i,
                    wordDao.getWordsCount(i,0)));
    }

    public void changeFragment(int level){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (level){
            case 0:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(0));
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(1));
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(2));
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(3));
                fragmentTransaction.commit();
                break;
            case 4:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(4));
                fragmentTransaction.commit();
                break;
            case 5:
                fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(5));
                fragmentTransaction.commit();
                break;
        }
    }
}