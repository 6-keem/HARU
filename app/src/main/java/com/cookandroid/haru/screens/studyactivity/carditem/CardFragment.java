package com.cookandroid.haru.screens.studyactivity.carditem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.haru.database.tableclass.word.Word;

public abstract class CardFragment extends Fragment {
    protected CustomOnClickListener customOnClickListener = null;
    protected CustomCheckButtonOnClickListener customCheckButtonOnClickListener = null;
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

    public interface CustomCheckButtonOnClickListener{
        void onClick(View view);
    }

    public void setCustomOnClickListener(CustomOnClickListener listener){
        this.customOnClickListener = listener;
    }

    public void setCustomCheckButtonOnClickListener(CustomCheckButtonOnClickListener listener){
        this.customCheckButtonOnClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        customOnClickListener = null;
    }
}
