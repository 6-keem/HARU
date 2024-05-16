package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.cookandroid.jlptvocabularyapplication.screens.StudyActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChapterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChapterFragment extends Fragment {
    private int level, totalWordCount, count;
    private int factor = 150, lastCount = 150;
    private ArrayList<ChapterData> chapterDataArrayList = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    public ChapterFragment(Context context, int level, int totalWordCount) {
        this.level = level;
        this.totalWordCount = totalWordCount;
        setWordCount();
        // TODO: 2024-05-14 쿼리로 진행도 가져오기
        try {
            for(int i = 0 ; i < count ; i ++){
                ChapterData chapterData = null;
                Drawable background = null;
                int imageID = 0;
                if(i == 0)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_test),
                            R.drawable.chapter_test,"JLPT 5","",40,150);
                else if(i == 1)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_1),
                            R.drawable.chapter_test,"JLPT 5","",40,150);
                else if(i == 2)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_2),
                            R.drawable.chapter_1,"UNIT 1","",20,150);
                else if(i == 3)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_3),
                            R.drawable.chapter_2,"UNIT 2","",60,150);
                else if(i == 4)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_4),
                            R.drawable.chapter_3,"UNIT 3","",44,150);
                else if(i == 5)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_5),
                            R.drawable.chapter_4,"UNIT 4","",22,150);
                else if(i == 6)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_6),
                            R.drawable.chapter_5,"UNIT 5","",95,150);
                else if(i == 7)
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_7),
                            R.drawable.chapter_6,"UNIT 6","",130,150);
                else
                    chapterData = new ChapterData(context.getDrawable(R.drawable.style_unit_8),
                            R.drawable.chapter_7,"UNIT 7","",150,150);
                chapterDataArrayList.add(chapterData);
            }
        } catch (NullPointerException e){ }
    }

    private void setWordCount(){
        if(totalWordCount > 1200) {
            factor = (((totalWordCount / 8) / 10) + 1) * 10;
            lastCount = totalWordCount - factor * 7;
            count = 9;
        } else {
            count = totalWordCount / 150 + 1;
        }
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
        View view = inflater.inflate(R.layout.chapter_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chapter_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ChapterRecyclerViewAdapter chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterDataArrayList);
        recyclerView.setAdapter(chapterRecyclerViewAdapter);

        chapterRecyclerViewAdapter.setOnItemClickListener(new ChapterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), StudyActivity.class).setAction("your.custom.action");
                intent.putExtra("level", level);
                intent.putExtra("factor", factor);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return view;
    }
}