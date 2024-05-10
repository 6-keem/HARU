package com.cookandroid.jlptvocabularyapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Vector;

public class LevelRecyclerViewAdapter extends RecyclerView.Adapter<LevelRecyclerViewAdapter.LevelViewHolder> {

    private ArrayList<String> levelButtonDataArrayList = null;

    public LevelRecyclerViewAdapter(ArrayList<String> levelButtonDataArrayList){
        this.levelButtonDataArrayList = levelButtonDataArrayList;
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
        Vector<Button> buttonVector = holder.buttonVector;
        buttonVector.get(0).setText("ALL");
        buttonVector.get(1).setText("N5");
        buttonVector.get(2).setText("N4");
        buttonVector.get(3).setText("N3");
        buttonVector.get(4).setText("N2");
        buttonVector.get(5).setText("N1");
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setButtonText(ArrayList<String> list){
        this.levelButtonDataArrayList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return levelButtonDataArrayList.size()+1;
    }

    public static class LevelViewHolder extends RecyclerView.ViewHolder{
        Vector<Button> buttonVector = new Vector<>();
        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            try{
                buttonVector.add(((Button) itemView.findViewById(R.id.level_all)));
                buttonVector.add(((Button) itemView.findViewById(R.id.level_n5)));
                buttonVector.add(((Button) itemView.findViewById(R.id.level_n4)));
                buttonVector.add(((Button) itemView.findViewById(R.id.level_n3)));
                buttonVector.add(((Button) itemView.findViewById(R.id.level_n2)));
                buttonVector.add(((Button) itemView.findViewById(R.id.level_n1)));
            } catch (Exception ignore){ }
        }
    }
}
