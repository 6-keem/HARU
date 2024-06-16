package com.cookandroid.haru.screens.studyactivity.carditem;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.haru.R;
import com.cookandroid.haru.database.WordsDatabase;
import com.cookandroid.haru.database.tableclass.word.Word;
import com.cookandroid.haru.database.tableclass.word.WordDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCardFragment extends CardFragment {

    private ArrayList<Button> buttonArrayList = new ArrayList<>();
    private ArrayList<String> problemArrayList = new ArrayList<>();
    private WordDao wordDao = WordsDatabase.getInstance(getContext()).wordDao();
    private int [] answerIDs = {R.id.answer1,R.id.answer2,R.id.answer3,R.id.answer4};
    private String answer;
    public TestCardFragment(){}
    public TestCardFragment(Word word){ super(word); }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.study_test_pager, container, false);
        setCardLayout(view);
        return view;
    }

    @Override
    protected void setCardLayout(View view) {
        WordDao wordDao = WordsDatabase.getInstance(getContext()).wordDao();
        int r = cardData.getKanji() == null ? 2 : 3;
        switch ((int)(Math.random() * r)){
            case 0:
                createKanjiProblem(view);
                break;
            case 1:
                createMeaningProblem(view);
                break;
            case 2:
                createFuriganaProblem(view);
                break;
        }
    }

    // Question : Meaning / Answer : Kanji
    private void createKanjiProblem(View view){
        TextView question = (TextView) view.findViewById(R.id.qusetion);
        String meaning = cardData.getWordMeaning().get(0);
        question.setText(transferMeaning(cardData.getWordMeaning()));

        // kanji가 없을 때 furigana로 대체
        List<Word> problems = wordDao.getWordForKanjiProblem(meaning,3);
        answer = transferKanji(cardData.getJapanese());
        problemArrayList.add(answer);
        for(int i = 0 ; i < problems.size() ; i ++) {
            if (problems.get(i).getKanji() != null)
                problemArrayList.add(transferKanji(problems.get(i).getKanji()));
            else
                problemArrayList.add(problems.get(i).getFurigana());
        }
        setProblems(view);
    }
    // Question : Kanji / Answer : Meaning
    private void createMeaningProblem(View view){
        TextView question = (TextView) view.findViewById(R.id.qusetion);
        String japnese = cardData.getJapanese();
        question.setText(transferKanji(cardData.getJapanese()));

        List<Word> problems = wordDao.getWordForMeaningProblem(japnese.substring(0,1),
                cardData.getFurigana().substring(0,1), cardData.getFurigana(), 3);
        answer = transferMeaning(cardData.getWordMeaning());
        problemArrayList.add(answer);
        while(problems.size() < 3){
            Word word = wordDao.getWordForMeaningProblem(cardData.getFurigana());
            boolean flag = true;
            for(Word w : problems){
                if(word.getFurigana().equals(w.getFurigana())){
                    flag = false;
                    break;
                }
            }
            if(flag)
                problems.add(word);
        }
        for(Word word : problems) {
            problemArrayList.add(transferMeaning(word.getWordMeaning()));
        }
        setProblems(view);
    }

    // if doesn't exist kanji -> other problem
    // Question : Kanji / Answer : Furigana
    private void createFuriganaProblem(View view){
        TextView question = (TextView) view.findViewById(R.id.qusetion);
        String japanese = cardData.getJapanese();
        String furigana = cardData.getFurigana();
        question.setText(transferKanji(japanese));
        answer = furigana;
        problemArrayList.add(furigana);
        // 중복 혹은 단어 개수 부족할 경우 체크
        int length = furigana.length();
        List<String> problems = cardData.getKanji() == null ?
                wordDao.getWordsFuriganaProblemWhenFurigana(furigana.substring(length-1,length), furigana, 3)
                : wordDao.getWordsFuriganaProblem(furigana.substring(length-1,length), furigana, 3);

        while(problems.size() < 3){
            String string = wordDao.getWordsFuriganaProblemWhenFurigana(cardData.getFurigana());
            boolean flag = true;
            for(String s : problems){
                if(s.equals(string)){
                    flag = false;
                    break;
                }
            }
            if(flag)
                problems.add(string);
        }
        problemArrayList.addAll(problems);
        setProblems(view);
    }

    private void setProblems(View view){
        Collections.shuffle(problemArrayList);
        for(int i = 0 ; i < problemArrayList.size() ; i ++){
            Button button = (Button) view.findViewById(answerIDs[i]);
            button.setText(problemArrayList.get(i));
            buttonArrayList.add(button);
            button.setOnClickListener(v -> {
                if(setCheckIsCorrect(button))
                    customCheckButtonOnClickListener.onClick(v);
                new Handler().postDelayed(() -> {
                    if(v != null)
                        customOnClickListener.onClick(v);
                }, 1000);
            });
        }
    }

    private boolean setCheckIsCorrect(Button button) {
        boolean flag = true;
        try{
            button.setOnClickListener(null);
            if(answer.equals(button.getText().toString()))
                button.setBackground(getContext().getDrawable(R.drawable.style_card_correct));
            else {
                flag = false;
                for(Button btn : buttonArrayList){
                    btn.setOnClickListener(null);
                    if(answer.equals(btn.getText().toString())){
                        btn.setBackground(getContext().getDrawable(R.drawable.style_card_correct));
                        btn.setTextColor(Color.WHITE);
                    }
                }
                button.setBackground(getContext().getDrawable(R.drawable.style_card_wrong));
            }
            button.setTextColor(Color.WHITE);
        } catch (NullPointerException ignore){ }
        return flag;
    }
    private String transferMeaning(List<String> meanings){
        String meaning = meanings.get(0);
        int index = meaning.indexOf("1.");
        if(index != -1){
            int index2 = meaning.indexOf("2.");
            if(index2 != -1)
                meaning = meaning.substring(0,index2);
            meaning = meaning.substring(index+2);
        }
        index = meaning.indexOf(";");
        return index == -1 ? meaning : meaning.substring(0,index);
    }

    private String transferKanji(String kanji){
        int index = kanji.indexOf("·");
        if(index != -1)
            kanji = kanji.substring(0,index);
        return kanji;
    }
}
