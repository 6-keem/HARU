package com.cookandroid.jlptvocabularyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cookandroid.jlptvocabularyapplication.database.Word;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


    }
}