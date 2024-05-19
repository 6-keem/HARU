package com.cookandroid.jlptvocabularyapplication.screens.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.CardData;

public abstract class CardFragment extends Fragment {
    protected CustomOnClickListener customOnClickListener = null;
    protected CardData cardData = null;
    protected CardFragment(){}
    protected CardFragment(Word word){
        this.cardData = new CardData(word);
    }
    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState);

    protected abstract void setCardLayout(View view);

    public interface CustomOnClickListener {
        void onClick(View view);
    }

    public void setCustomOnClickListener(CustomOnClickListener listener){
        this.customOnClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        customOnClickListener = null;
    }
}
