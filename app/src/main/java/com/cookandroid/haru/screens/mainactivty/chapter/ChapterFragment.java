package com.cookandroid.haru.screens.mainactivty.chapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.haru.R;
import com.cookandroid.haru.database.WordsDatabase;
import com.cookandroid.haru.database.tableclass.userdata.UserData;
import com.cookandroid.haru.database.tableclass.userdata.UserDataDao;
import com.cookandroid.haru.database.tableclass.word.WordDao;
import com.cookandroid.haru.screens.studyactivity.StudyBookmarkActivity;
import com.cookandroid.haru.screens.studyactivity.StudyNormalActivity;
import com.cookandroid.haru.screens.studyactivity.StudyTestActivity;

import java.util.ArrayList;
import java.util.List;

public class ChapterFragment extends Fragment {
    private int level;
    private ArrayList<ChapterData> chapterDataArrayList = null;
    private ChapterRecyclerViewAdapter chapterRecyclerViewAdapter = null;
    private static final int testDrawable = R.drawable.test_shadow_background;
    private static final int chapterDrawable = R.drawable.chapter_shadow_background;
    public ChapterFragment(){ }
    @SuppressLint("UseCompatLoadingForDrawables")
    public ChapterFragment(Context context, int level) {
        this.level = level;
        setWordCount(context);
        setChapterFragmentItem(context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setChapterFragmentItem(Context context) {
        chapterDataArrayList = new ArrayList<>();
        try {
            UserDataDao userDataDao = WordsDatabase.getInstance(context).userDataDao();
            UserData data = userDataDao.getChapterData(Integer.toString(level), 0);
            if(level == 0)
                setBookmarkChapterFragment(context, userDataDao);
            else
                setNormalChapterFragment(context, userDataDao, data);
        } catch (NullPointerException e){ }
    }

    private void setNormalChapterFragment(Context context, UserDataDao userDataDao, UserData data) {
        int total = data.chapterCount;
        String drawableName = "test_level_" + level;
        int drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
        chapterDataArrayList.add(new TestChapterData(context.getDrawable(testDrawable), drawableId, data));

        for(int i = 1; i <= total ; i ++){
            drawableName = "chapter_n" + level + "_" + i;
            drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
            chapterDataArrayList.add(new NormalChapterData(context.getDrawable(chapterDrawable),
                    drawableId, userDataDao.getChapterData(Integer.toString(level), i)));
        }
    }

    private void setBookmarkChapterFragment(Context context, UserDataDao userDataDao) {
        String drawableName = "test_level_all";
        int drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
        chapterDataArrayList.add(new TestChapterData(context.getDrawable(testDrawable),
                drawableId, userDataDao.getChapterData(Integer.toString(level), 0)));
        for(int i = 5 ; i>= 1 ; i--){
            drawableName = "bookmark_level_" + i;
            drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

            WordDao wordDao = WordsDatabase.getInstance(context).wordDao();
            int count = wordDao.getBookmarkCount(i);
            chapterDataArrayList.add(new BookmarkChapterData(context.getDrawable(testDrawable),
                    drawableId, userDataDao.getChapterData(Integer.toString(i), 0),count));
        }
    }

    private List<UserData> setWordCount(Context context){
        UserDataDao userDataDao = WordsDatabase.getInstance(context).userDataDao();
        return userDataDao.getDatasEachLevel(Integer.toString(level));
    }

    @Override
    public void onResume() {
        super.onResume();
        setChapterFragmentItem(getContext());
        chapterRecyclerViewAdapter.update(chapterDataArrayList);
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
        View view = inflater.inflate(R.layout.chapter_recyclerview, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.chapter_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chapterRecyclerViewAdapter = new ChapterRecyclerViewAdapter(chapterDataArrayList);
        recyclerView.setAdapter(chapterRecyclerViewAdapter);

        chapterRecyclerViewAdapter.setOnItemClickListener((v, position) -> {
            onStop();
            Intent intent;
            if(position == 0)
                intent = new Intent(getActivity(), StudyTestActivity.class).setAction("STUDY_TEST");
            else if(level != 0)
                intent = new Intent(getActivity(), StudyNormalActivity.class).setAction("STUDY_NORMAL");
            else
                intent = new Intent(getActivity(), StudyBookmarkActivity.class).setAction("STUDY_BOOKMARK");
            intent.putExtra("level", level);
            intent.putExtra("position", position);
            startActivity(intent);
        });
        return view;
    }
}