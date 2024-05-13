package com.cookandroid.jlptvocabularyapplication.database.screens;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.jlptvocabularyapplication.R;

import java.util.ArrayList;

public class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterViewHolder> {

    private ArrayList<ChapterData> chapterData;
    private ArrayList<ImageView> imageViews;
    private ArrayList<TextView> titles, progress;
    private ArrayList<TextView> description;
    @NonNull
    @Override
    public ChapterRecyclerViewAdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_itmes, parent, false);
        return new ChapterRecyclerViewAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterRecyclerViewAdapter.ChapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//     Image, Title, Progressbar
static class ChapterViewHolder extends RecyclerView.ViewHolder{
        ArrayList<ChapterData> chapterData = new ArrayList<>();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ArrayList<TextView> description = new ArrayList<>();
        ArrayList<TextView> titles = new ArrayList<>();
        ArrayList<TextView> progress = new ArrayList<>();
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViews.add((ImageView) itemView.findViewById(R.id.image1));
            imageViews.add((ImageView) itemView.findViewById(R.id.image2));
            imageViews.add((ImageView) itemView.findViewById(R.id.image3));
            imageViews.add((ImageView) itemView.findViewById(R.id.image4));
            imageViews.add((ImageView) itemView.findViewById(R.id.image5));
            imageViews.add((ImageView) itemView.findViewById(R.id.image6));
            imageViews.add((ImageView) itemView.findViewById(R.id.image7));
            imageViews.add((ImageView) itemView.findViewById(R.id.image8));

            titles.add((TextView) itemView.findViewById(R.id.title1));
            titles.add((TextView) itemView.findViewById(R.id.title2));
            titles.add((TextView) itemView.findViewById(R.id.title3));
            titles.add((TextView) itemView.findViewById(R.id.title4));
            titles.add((TextView) itemView.findViewById(R.id.title5));
            titles.add((TextView) itemView.findViewById(R.id.title6));
            titles.add((TextView) itemView.findViewById(R.id.title7));
            titles.add((TextView) itemView.findViewById(R.id.title8));
//
//            description.add((TextView) itemView.findViewById(R.id.description1));
//            description.add((TextView) itemView.findViewById(R.id.description2));
//            description.add((TextView) itemView.findViewById(R.id.description3));
//            description.add((TextView) itemView.findViewById(R.id.description4));
//            description.add((TextView) itemView.findViewById(R.id.description5));
//            description.add((TextView) itemView.findViewById(R.id.description6);
//            description.add((TextView) itemView.findViewById(R.id.description7));


//            progress.add((TextView) itemView.findViewById(R.id.progress1));
//            progress.add((TextView) itemView.findViewById(R.id.progress2));
//            progress.add((TextView) itemView.findViewById(R.id.progress3));
//            progress.add((TextView) itemView.findViewById(R.id.progress4));
//            progress.add((TextView) itemView.findViewById(R.id.progress5));
//            progress.add((TextView) itemView.findViewById(R.id.progress6));
//            progress.add((TextView) itemView.findViewById(R.id.progress7));
        }
    }
}
