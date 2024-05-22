package com.cookandroid.jlptvocabularyapplication.screens.studyactivity;

import android.annotation.SuppressLint;

import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.TestPieChart;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.TestCardFragment;

import java.util.List;


@SuppressLint("SetTextI18n")
public class StudyTestActivity extends StudyActivity {
    @Override
    protected void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        List<Word> words = level != 0 ?
                wordDao.getWordForTest(Integer.toString(level))
                : wordDao.getWordForTest("_");
        wordEnd = 30;
        currentPage = 0;
        for (Word word : words)
            arrayList.add(new TestCardFragment(word));
    }

    @Override
    protected String setToolbarTitle() {
        return ("N" + level + " TEST");
    }

    @Override
    protected void onExit(int factor){
//        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
//        userDataDao.updateUserDate(retryCount + factor, currentPage, Integer.toString(level), position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}