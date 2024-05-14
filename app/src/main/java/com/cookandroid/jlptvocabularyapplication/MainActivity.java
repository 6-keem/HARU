package com.cookandroid.jlptvocabularyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterData;
import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterRecyclerViewAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.level.LevelRecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> buttonTextArraylist;
    private LevelRecyclerViewAdapter levelRecyclerViewAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_header);

        recyclerView = (RecyclerView)  findViewById(R.id.level_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        ArrayList<String> levelItems = setLevelItem();
        levelRecyclerViewAdapter = new LevelRecyclerViewAdapter(levelItems);
        recyclerView.setAdapter(levelRecyclerViewAdapter);


        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.chapter_recyclerview);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<ChapterData> chapterData = setChapterData();
        ChapterRecyclerViewAdapter chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterData);
        recyclerView1.setAdapter(chapterRecyclerViewAdapter);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    ArrayList<ChapterData> setChapterData(){
        ArrayList<ChapterData> chapterData = new ArrayList<>();
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_test),R.drawable.chapter_test,"JLPT 5","",40,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_1),R.drawable.chapter_test,"JLPT 5","",40,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_2),R.drawable.chapter_1,"UNIT 1","",20,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_3),R.drawable.chapter_2,"UNIT 2","",60,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_4),R.drawable.chapter_3,"UNIT 3","",44,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_5),R.drawable.chapter_4,"UNIT 4","",22,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_6),R.drawable.chapter_5,"UNIT 5","",95,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_7),R.drawable.chapter_6,"UNIT 6","",130,150));
        chapterData.add(new ChapterData(getApplicationContext().getDrawable(R.drawable.style_unit_8),R.drawable.chapter_7,"UNIT 7","",150,150));
        return chapterData;
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
}