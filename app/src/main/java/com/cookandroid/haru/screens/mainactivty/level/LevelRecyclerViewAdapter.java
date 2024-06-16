package com.cookandroid.haru.screens.mainactivty.level;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.haru.screens.mainactivty.MainActivity;
import com.cookandroid.haru.R;

import java.util.ArrayList;

public class LevelRecyclerViewAdapter extends RecyclerView.Adapter<LevelRecyclerViewAdapter.LevelViewHolder> {

    private ArrayList<String> stringArrayList = null;
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
    private MainActivity activity = null;
    public LevelRecyclerViewAdapter(MainActivity activity, ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_level_item,parent,false);
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
                int level = 0;
                for(int i = 0 ; i < toggleButtons.size() ; i ++){
                    if(compoundButton.equals(toggleButtons.get(i)))
                        level = i;
                    toggleButtons.get(i).setBackground(itemView.getResources().getDrawable(R.drawable.style_level_button));
                    toggleButtons.get(i).setTextColor(itemView.getResources().getColor(R.color.button_text_color));
                }
                compoundButton.setBackground(itemView.getResources().getDrawable(R.drawable.style_level_button_checked));
                compoundButton.setTextColor(itemView.getResources().getColor(R.color.button_checked_text_color));
                activity.changeFragment(level);
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
