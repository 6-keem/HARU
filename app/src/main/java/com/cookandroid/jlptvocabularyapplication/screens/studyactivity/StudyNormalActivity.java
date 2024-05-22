package com.cookandroid.jlptvocabularyapplication.screens.studyactivity;

import android.annotation.SuppressLint;
import android.widget.TextView;


import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.StudyPieChart;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.TestPieChart;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.NormalCardFragment;

import java.util.List;

@SuppressLint("SetTextI18n")
public class StudyNormalActivity extends StudyActivity {
    private int checkCount = 0;
    @Override
    protected void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        UserData userData = userDataDao.getChapterData(Integer.toString(level), position);

        TextView textView = (TextView)findViewById(R.id.retry_count);
        textView.setText(Integer.toString(userData.count + 1));

        int beginID = wordDao.getLevelsFirstWordID(Integer.toString(level));
        int begin = beginID + (userData.total * (position - 1));
        int end = begin + userData.total;
        wordEnd = userData.total;
        retryCount = userData.count;

        List<Word> words = wordDao.getWords(Integer.toString(level), begin, end);
        for (Word word : words){
            arrayList.add(new NormalCardFragment(word));
        }
    }

    @Override
    protected String setToolbarTitle() {
        return ("N" + level + " UNIT " + position);
    }

    @Override
    protected void onExit(int factor){
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        UserData userData = userDataDao.getChapterData(Integer.toString(level), position);
        int studiedCount = currentPage - userData.getStudiedCount();
        userDataDao.updateUserDate(retryCount + factor, (currentPage + 1), Integer.toString(level), position);
        if(studiedCount > 0){
            userDataDao.updateChapterTestData(Integer.toString(level), 0, (studiedCount + 1));
            userDataDao.updateChapterTestData(Integer.toString(0),0, (studiedCount)+1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}