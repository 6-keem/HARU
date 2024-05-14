package com.cookandroid.jlptvocabularyapplication.screens.level;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.jlptvocabularyapplication.R;

import java.util.ArrayList;

public class LevelRecyclerViewAdapter extends RecyclerView.Adapter<LevelRecyclerViewAdapter.LevelViewHolder> {

    private ArrayList<String> stringArrayList = null;
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
    public LevelRecyclerViewAdapter(ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_items,parent,false);
        return new LevelViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        holder.onBind(stringArrayList.get(position));
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setButtonText(ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder{
        ToggleButton toggleButton;
        @SuppressLint("UseCompatLoadingForDrawables")
        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.level_item);
            toggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
                for(ToggleButton button : toggleButtons) {
                    button.setBackground(itemView.getResources().getDrawable(R.drawable.style_level_button));
                    button.setTextColor(itemView.getResources().getColor(R.color.button_text_color));
                }
                compoundButton.setBackground(itemView.getResources().getDrawable(R.drawable.style_level_button_checked));
                compoundButton.setTextColor(itemView.getResources().getColor(R.color.button_checked_text_color));
            });
            if(toggleButtons.size() == 0)
                toggleButton.setChecked(true);
            toggleButtons.add(toggleButton);
        }
        void onBind(String text){
            toggleButton.setText(text);
            toggleButton.setTextOn(text);
            toggleButton.setTextOff(text);
        }
    }
}
