package com.cookandroid.jlptvocabularyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        buttonTextArraylist = new ArrayList<>();
        levelRecyclerViewAdapter = new LevelRecyclerViewAdapter(buttonTextArraylist);
        recyclerView.setAdapter(levelRecyclerViewAdapter);
    }
}