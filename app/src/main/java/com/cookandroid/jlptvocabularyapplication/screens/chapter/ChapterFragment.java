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

public class ChapterFragment extends Fragment {
    private int level, totalWordCount, count;
    private int factor = 150, lastCount = 150;
    private ArrayList<ChapterData> chapterDataArrayList = new ArrayList<>();
    private int[] styles = {R.drawable.style_unit_test, R.drawable.style_unit_1, R.drawable.style_unit_2,
            R.drawable.style_unit_3, R.drawable.style_unit_4,R.drawable.style_unit_5,
            R.drawable.style_unit_6,R.drawable.style_unit_7, R.drawable.style_unit_8 };

    private int[] icon = {R.drawable.chapter_test, R.drawable.chapter_1, R.drawable.chapter_2,
            R.drawable.chapter_3,R.drawable.chapter_4,R.drawable.chapter_5,
            R.drawable.chapter_6,R.drawable.chapter_7,R.drawable.chapter_7};
    @SuppressLint("UseCompatLoadingForDrawables")
    public ChapterFragment(Context context, int level, int totalWordCount) {
        this.level = level;
        this.totalWordCount = totalWordCount;
        setWordCount();
        // TODO: 2024-05-14 쿼리로 진행도 가져오기
        try {
            for(int i = 0 ; i < count ; i ++){
                ChapterData chapterData = null;
                if(i == 0)
                    chapterData = new ChapterData(context.getDrawable(styles[i]),
                            icon[i], ("JLPT " + level), "", 0, 0 );
                else
                    chapterData = new ChapterData(context.getDrawable(styles[i]),
                            icon[i], ("UNIT " + i), "", 0, 0 );

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