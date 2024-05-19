package com.cookandroid.jlptvocabularyapplication.screens.study.normalcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Sentence;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class NormalCardFragment extends CardFragment {
    private LinearLayout front, back;
    public NormalCardFragment(){}
    public NormalCardFragment(Word word){
        super(word);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.study_pager, container, false);
        setCardLayout(view);
        return view;
    }

    @Override
    protected void setCardLayout(View view) {
        setFrontCard(view);
        setBackCard(view);
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
                customOnClickListener.onClick(v);
        });

        checkButton.setOnClickListener(v -> {
            if(v != null) {
                float scale = getContext().getResources().getDisplayMetrics().density;
                front.setCameraDistance(scale * 8000);
                back.setCameraDistance(scale * 8000);
                ((Button) front.findViewById(R.id.skip_front)).setVisibility(View.GONE);
                ((Button) front.findViewById(R.id.check)).setVisibility(View.GONE);
                Animator frontAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.flipfront_animator);
                Animator backAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.flipback_animator);

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
        ImageButton ttsButton = (ImageButton) view.findViewById(R.id.tts_button);
        ImageButton naverButton = (ImageButton) view.findViewById(R.id.naver);

        wordClass.setText(cardData.getWordClass());
        if(cardData.getKanji() == null)
            furigana.setVisibility(View.INVISIBLE);
        else
            furigana.setText(cardData.getFurigana());

        try {
            List<Sentence> sentenceList = cardData.getSentences();
            sentence.setText("ㆍ" + sentenceList.get(0).getJpSentence());
            sentenceMeaning.setText("ㆍ" + sentenceList.get(0).getKrSentence());
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
                customOnClickListener.onClick(v);
        });
        naverButton.setOnClickListener(v -> {
            String link = "https://ja.dict.naver.com/#/entry/jako/" + cardData.getLink();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, getContext().getPackageName());
            startActivity(intent);
        });
        ttsButton.setOnClickListener(v -> {
            MyTextToSpeech.getInstance(getContext())
                    .speak(cardData.getFurigana(),TextToSpeech.QUEUE_ADD,null,null);
        });
    }

    public void setCustomOnClickListener(CustomOnClickListener listener){
        this.customOnClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cardData = null;
        front = back = null;
    }
}
