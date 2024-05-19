package com.cookandroid.jlptvocabularyapplication.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterFragment;
import com.cookandroid.jlptvocabularyapplication.screens.level.LevelRecyclerViewAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ArrayList<ChapterFragment> fragmentArrayList = new ArrayList<>();
    ArrayList<CardFragment> arrayList = new ArrayList<>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeUserData();

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

    private void initializeUserData(){
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();

        List<UserData> userDatas = userDataDao.getAllUserData();
        if(userDatas.size() == 0){
            // TODO: 2024-05-18 전체 단어 개수 넣어야함
            userDataDao.insertUserData(new UserData(0,0, 0));
            for(int i = 1 ; i < 6 ; i ++){
                int total = wordDao.getWordsCount(i,0);
                if(total <= 1200){
                    int iterationMax = total / 150;
                    UserData userData = new UserData(i, 0, total);
                    userData.chapterCount = iterationMax;
                    userDataDao.insertUserData(userData);
                    for(int j = 1 ; j < iterationMax ; j ++)
                        userDataDao.insertUserData(new UserData(i,j,150));
                    userDataDao.insertUserData(new UserData(i, iterationMax, total % 150));
                } else {
                    int factor = (total / 8) + 1;
                    UserData userData = new UserData(i, 0, total);
                    userData.chapterCount = 8;
                    userDataDao.insertUserData(userData);
                    for(int j = 1 ; j < 8 ; j ++)
                        userDataDao.insertUserData(new UserData(i,j,factor));
                    userDataDao.insertUserData(new UserData(i,8,total % factor));
                }
            }
        }
    }

    private void setChapterFragments(){
        fragmentArrayList.add(new ChapterFragment(getApplicationContext(),0));
        for(int i = 5 ; i >= 1 ; i--)
            fragmentArrayList.add(new ChapterFragment(getApplicationContext(), i));
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