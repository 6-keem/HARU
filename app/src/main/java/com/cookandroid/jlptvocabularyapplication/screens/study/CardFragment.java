package com.cookandroid.jlptvocabularyapplication.screens.study;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.Sentence;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.Word;

import java.util.ArrayList;
import java.util.List;

public class CardFragment extends Fragment {
    private LinearLayout front, back;
    private CardData cardData;
    private SkipButtonOnClickListener skipButtonOnClickListener = null;

    public CardFragment(Word word){
        this.cardData = new CardData(word);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_card_item, container, false);
        setFrontCard(view);
        setBackCard(view);
        return view;
    }

    private void setFrontCard(View view){
        front = (LinearLayout) view.findViewById(R.id.card_front);
        Button checkButton = (Button) view.findViewById(R.id.check);
        Button skipButton = (Button) view.findViewById(R.id.skip_front);
        TextView textView = (TextView) view.findViewById(R.id.japanese_front);
        String japanese = cardData.getJapanese();
        textView.setText(japanese);
        skipButton.setOnClickListener(v -> {
            if(v != null)
                skipButtonOnClickListener.onSkipClick(v);
        });

        checkButton.setOnClickListener(v -> {
            if(v != null) {
                float scale = getContext().getResources().getDisplayMetrics().density;
                front.setCameraDistance(scale * 8000);
                back.setCameraDistance(scale * 8000);
                ((Button) front.findViewById(R.id.skip_front)).setVisibility(View.GONE);
                ((Button) front.findViewById(R.id.check)).setVisibility(View.GONE);
                Animator frontAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
                Animator backAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);

                frontAnimator.setTarget(front);
                backAnimator.setTarget(back);
                frontAnimator.start();
                backAnimator.start();
            }
        });
    }

    private void setBackCard(View view){
        back = (LinearLayout) view.findViewById(R.id.card_back);
        Button skipButton = (Button) view.findViewById(R.id.skip_back);
        TextView meaning = (TextView) view.findViewById(R.id.meaning);
        TextView sentence = (TextView) view.findViewById(R.id.sentence);
        TextView furigana = (TextView) view.findViewById(R.id.furigana);
        TextView wordClass = (TextView) view.findViewById(R.id.word_class);
        TextView japanese = (TextView) view.findViewById(R.id.japanese_back);
        TextView sentenceMeaning = (TextView) view.findViewById(R.id.sentence_meaning);

        wordClass.setText(cardData.getWordClass());
        if(cardData.getKanji() == null)
            furigana.setVisibility(View.INVISIBLE);
        else
            furigana.setText(cardData.getFurigana());

        try {
            List<Sentence> sentenceList = cardData.getSentences();
            sentence.setText(sentenceList.get(0).getJpSentence());
            sentenceMeaning.setText("-" + sentenceList.get(0).getKrSentence());
        } catch (IndexOutOfBoundsException e){
            sentence.setVisibility(View.GONE);
            sentenceMeaning.setVisibility(View.GONE);
        }

        ArrayList<String> meanigns = new ArrayList<>();
        String mean = cardData.getWordMeaning().get(0);
        meaning.setText(mean);
        japanese.setText(cardData.getJapanese());
        skipButton.setOnClickListener(v -> {
            if(v != null)
                skipButtonOnClickListener.onSkipClick(v);
        });
    }

    public interface SkipButtonOnClickListener {
        void onSkipClick(View view);
    }
    public void setSkipButtonOnClickListener(SkipButtonOnClickListener listener){
        this.skipButtonOnClickListener = listener;
    }

}
