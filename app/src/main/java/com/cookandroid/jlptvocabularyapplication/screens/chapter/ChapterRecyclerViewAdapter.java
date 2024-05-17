package com.cookandroid.jlptvocabularyapplication.screens.chapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.jlptvocabularyapplication.R;

import java.util.ArrayList;

public class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterViewHolder> {
    private OnItemClickListener mListener = null;
    private ArrayList<ChapterData> chapterData;
    @SuppressLint("NotifyDataSetChanged")
    public ChapterRecyclerViewAdapter(ArrayList<ChapterData> chapterData){
        this.chapterData = chapterData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_fragment_item, parent, false);
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
    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView chapterTitle, description;
        ProgressBar progressBar;
        ConstraintLayout constraintLayout;
        ImageView imageView;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);

            chapterTitle = (TextView) itemView.findViewById(R.id.chapter_title);
//            description = (TextView) itemView.findViewById(R.id.de)
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
            imageView = (ImageView) itemView.findViewById(R.id.chapter_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.chapter_progress_bar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null)
                            mListener.onItemClick(view, position);
                    }
                }
            });
        }
        public void onBind(ChapterData chapterData){
            chapterTitle.setText(chapterData.getTitle());
            progressBar.setProgress(chapterData.getCount());
            progressBar.setMax(chapterData.getTotal());
            constraintLayout.setBackground(chapterData.getBackground());
            imageView.setImageResource(chapterData.getImageID());
        }
    }

}
