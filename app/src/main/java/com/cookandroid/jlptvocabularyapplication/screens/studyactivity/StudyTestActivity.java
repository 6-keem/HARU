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
    protected void setPageScrollEvent() {
        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setCustomOnClickListener(view -> toNextPage());
            cardFragment.setCustomCheckButtonOnClickListener(view -> checkCount++ );
        }
    }

    private void toNextPage() {
        if(currentPage == wordEnd-1){
            chronometer.stop();
            // TODO: 2024-05-20 테스트 종료 후 결과 저장하기 위함
            //onExit(1);
            pieChartDialog = new TestPieChart(StudyTestActivity.this, checkCount,
                    wordEnd, level, position, chronometer.getText().toString(), dialogConfrimListener);
            pieChartDialog.show();
        }
        else {
            viewPager.setCurrentItem(++currentPage, true);
            progressBar.setProgress(currentPage+1);
            currentCount.setText(Integer.toString(currentPage+1));
        }
    }

    @Override
    protected void onExit(int factor){
        UserDataDao userDataDao = WordsDatabase.getInstance(getApplicationContext()).userDataDao();
        userDataDao.updateUserDate(retryCount + factor, currentPage, Integer.toString(level), position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
    }
}