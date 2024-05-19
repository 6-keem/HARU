package com.cookandroid.jlptvocabularyapplication.screens;

import android.annotation.SuppressLint;

import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.testcard.TestCardFragment;

import java.util.List;


@SuppressLint("SetTextI18n")
public class StudyTestActivity extends StudyActivity {
    @Override
    protected void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        List<Word> words = level != 0 ?
                wordDao.getWordForTest(Integer.toString(level))
                : wordDao.getWordForTest("_");
        wordEnd = 20;
        currentPage = 0;
        for (Word word : words)
            arrayList.add(new TestCardFragment(word));
    }
    @Override
    protected void setScrollEvent() {
        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setCustomOnClickListener(view -> {
                viewPager.setCurrentItem(++currentPage, true);
                progressBar.setProgress(currentPage);
                currentCount.setText(Integer.toString(currentPage));
                if(currentPage == wordEnd){
                    // TODO: 2024-05-18 팝업 띄우고 종료하기
                    chronometer.stop();
                    onExit(1);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onExit(int factor){
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        userDataDao.updateUserDate(retryCount + factor, currentPage - 1, level, position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}