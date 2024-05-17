package com.cookandroid.jlptvocabularyapplication.screens.study;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cookandroid.jlptvocabularyapplication.R;

import java.util.ArrayList;

public class CardPagerAdapter extends FragmentStateAdapter {

    private ArrayList<CardFragment> fragmentArrayList = new ArrayList<>();

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<CardFragment> fragmentArrayList) {
        super(fragmentActivity);
        this.fragmentArrayList = fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() { return fragmentArrayList.size(); }

    public void addFragment(CardFragment fragment){
        fragmentArrayList.add(fragment);
        this.notifyItemInserted(fragmentArrayList.size() - 1);
    }


}
