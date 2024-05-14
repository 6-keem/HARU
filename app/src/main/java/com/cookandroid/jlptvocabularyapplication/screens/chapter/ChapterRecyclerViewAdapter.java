package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.jlptvocabularyapplication.R;

import java.util.ArrayList;

public class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterViewHolder> {

    private ArrayList<ChapterData> chapterData;
    @SuppressLint("NotifyDataSetChanged")
    public ChapterRecyclerViewAdapter(ArrayList<ChapterData> chapterData){
        this.chapterData = chapterData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_itmes, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.onBind(chapterData.get(position));
    }

    @Override
    public int getItemCount() {
        return chapterData.size();
    }
//     Image, Title, Progressbar
    public static class ChapterViewHolder extends RecyclerView.ViewHolder{
        TextView chapterTitle, description;
        ProgressBar progressBar;
        LinearLayout linearLayout;
        ImageView imageView;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);

            chapterTitle = (TextView) itemView.findViewById(R.id.chapter_title);
//            description = (TextView) itemView.findViewById(R.id.de)
            linearLayout = (LinearLayout) itemView.findViewById(R.id.chapter_background);
            imageView = (ImageView) itemView.findViewById(R.id.chapter_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.chapter_progress_bar);
        }
        public void onBind(ChapterData chapterData){
            chapterTitle.setText(chapterData.getTitle());
            progressBar.setProgress(chapterData.getCount());
            progressBar.setMax(chapterData.getTotal());
            linearLayout.setBackground(chapterData.getBackground());
            imageView.setImageResource(chapterData.getImageID());
        }
    }

}
