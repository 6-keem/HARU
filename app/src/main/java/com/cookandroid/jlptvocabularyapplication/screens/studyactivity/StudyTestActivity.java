package com.cookandroid.jlptvocabularyapplication.screens.studyactivity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.StudyPieChart;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.TestPieChart;
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

        TextView title = (TextView) findViewById(R.id.study_title);
        TextView retryCount = (TextView)findViewById(R.id.retry_count);
        if(level == 0)
            retryCount.setText("전체");
        else
            retryCount.setText("N" +level);
        title.setText(" 단어 시험");

        wordEnd = 30;
        currentPage = 0;
        for (Word word : words)
            arrayList.add(new TestCardFragment(word));
    }

    @Override
    protected String setToolbarTitle() {
        if(level==0)
            return ("N1~N5 TEST");
        return ("N" + level + " TEST");
    }

    @Override
    protected void setPieChartDialog() {
        pieChartDialog = new TestPieChart(StudyTestActivity.this, wordEnd - checkCount,
                wordEnd, level, position, chronometer.getText().toString(), dialogConfrimListener);
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