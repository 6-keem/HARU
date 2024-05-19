package com.cookandroid.jlptvocabularyapplication.screens;

import android.annotation.SuppressLint;
import android.widget.TextView;


import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.study.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.study.normalcard.NormalCardFragment;

import java.util.List;

@SuppressLint("SetTextI18n")
public class StudyNormalActivity extends StudyActivity {

    @Override
    protected void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        UserData userData = userDataDao.getChapterData(level, position);

        TextView textView = (TextView)findViewById(R.id.retry_count);
        textView.setText(Integer.toString(userData.count + 1));

        int beginID = wordDao.getLevelsFirstWordID(Integer.toString(level));
        int begin = beginID + (userData.total * (position - 1));
        int end = begin + userData.total;
        wordEnd = userData.total - 1;
        retryCount = userData.count;

        List<Word> words = wordDao.getWords(Integer.toString(level), begin, end);
        for (Word word : words){
            arrayList.add(new NormalCardFragment(word));
        }
    }

    @Override
    protected void setScrollEvent() {
        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setCustomOnClickListener(view -> {
                if(currentPage == wordEnd){
                    // TODO: 2024-05-18 팝업 띄우고 종료하기
                    chronometer.stop();
                    onExit(1);
                    finish();
                }
                viewPager.setCurrentItem(++currentPage, true);
                progressBar.setProgress(currentPage);
                currentCount.setText(Integer.toString(currentPage));
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