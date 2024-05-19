package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.screens.StudyNormalActivity;
import com.cookandroid.jlptvocabularyapplication.screens.StudyTestActivity;

import java.util.ArrayList;
import java.util.List;

public class ChapterFragment extends Fragment {
    private int level;
    private ArrayList<ChapterData> chapterDataArrayList = new ArrayList<>();
    private int[] styles = {R.drawable.chapter_shadow_test, R.drawable.chapter_shadow_level_1, R.drawable.chapter_shadow_level_2,
            R.drawable.chapter_shadow_level_3, R.drawable.chapter_shadow_level_4,R.drawable.chapter_shadow_level_5,
            R.drawable.chapter_shadow_level_6,R.drawable.chapter_shadow_level_7, R.drawable.chapter_shadow_level_7 };

    private int[] icon = {R.drawable.chapter_test, R.drawable.chapter_1, R.drawable.chapter_2,
            R.drawable.chapter_3,R.drawable.chapter_4,R.drawable.chapter_5,
            R.drawable.chapter_6,R.drawable.chapter_7,R.drawable.chapter_7};

    public ChapterFragment(){ }
    @SuppressLint("UseCompatLoadingForDrawables")
    public ChapterFragment(Context context, int level) {
        this.level = level;
        setWordCount(context);
        // TODO: 2024-05-14 쿼리로 진행도 가져오기
        try {
            UserDataDao userDataDao = WordsDatabase.getInstance(context).userDataDao();
            UserData data = userDataDao.getChapterData(level, 0);
            if(level == 0){
                chapterDataArrayList.add(new ChapterData(context.getDrawable(styles[0]),
                        icon[0], "JLPT ALL", "", userDataDao.getChapterData(level, 0)));
            }
            else {
                int total = data.chapterCount;
                for(int i = 0; i <= total ; i ++){
                    if(i == 0) {
                        chapterDataArrayList.add(new ChapterData(context.getDrawable(styles[i]),
                                icon[i], ("JLPT " + level), "", data));
                    } else {
                        chapterDataArrayList.add(new ChapterData(context.getDrawable(styles[i]),
                                icon[i], ("UNIT " + i), "", userDataDao.getChapterData(level, i)));
                    }
                }
            }
        } catch (NullPointerException e){ }
    }
    private List<UserData> setWordCount(Context context){
        UserDataDao userDataDao = WordsDatabase.getInstance(context).userDataDao();
        return userDataDao.getDatasEachLevel(level);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chapter_recyclerview, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chapter_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ChapterRecyclerViewAdapter chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterDataArrayList);
        recyclerView.setAdapter(chapterRecyclerViewAdapter);

        chapterRecyclerViewAdapter.setOnItemClickListener((v, position) -> {
            Intent intent;
            if(position == 0)
                intent = new Intent(getActivity(), StudyTestActivity.class).setAction("Study_Test");
            else
                intent = new Intent(getActivity(), StudyNormalActivity.class).setAction("Study_NORMAL");
            intent.putExtra("level", level);
            intent.putExtra("position", position);
            startActivity(intent);
        });
        return view;
    }
}