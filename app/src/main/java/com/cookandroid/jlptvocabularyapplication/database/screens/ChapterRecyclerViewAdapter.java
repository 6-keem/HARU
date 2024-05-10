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
        ImageView imageView;
        TextView title, description;
        ProgressBar progressBar;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView = (ImageView) itemView.findViewById(R.id._chapter_card_img);
//            title = (TextView) itemView.findViewById(R.id.chapter_card_title);
//            description = (TextView) itemView.findViewById(R.id.chapter_card_description);
//            progressBar = (ProgressBar) itemView.findViewById(R.id.chatper_card_progressbar);
        }
    }
}
