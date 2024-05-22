package com.cookandroid.jlptvocabularyapplication.screens.studyactivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.WordDao;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.ErrorDialog;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.NormalCardFragment;

import java.util.List;

@SuppressLint("SetTextI18n")
public class StudyBookmarkActivity extends StudyActivity {
    private int checkCount = 0;
    private View.OnClickListener mConfirmListener = v-> finish();
    private ErrorDialog errorDialog;
    @Override
    protected void setCardItem(){
        WordDao wordDao = WordsDatabase.getInstance(getApplicationContext()).wordDao();
        List<Word> words = wordDao.getBookmarkedWords(Integer.toString(6-position));

        if(words.size() == 0){
            errorDialog = new ErrorDialog(StudyBookmarkActivity.this, mConfirmListener);
            errorDialog.show();
        }

        TextView title = (TextView) findViewById(R.id.study_title);
        TextView retryCount = (TextView)findViewById(R.id.retry_count);
        retryCount.setText("N" + (6-position));
        title.setText(" 북마크 복습중");

        wordEnd = words.size();

        for (Word word : words){
            arrayList.add(new NormalCardFragment(word));
        }
    }

    @Override
    protected String setToolbarTitle() {
        return "N" + (6-position) + " 북마크";
    }

    @Override
    protected void onExit(int factor){ }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
        if(errorDialog != null && errorDialog.isShowing())
            errorDialog.dismiss();
    }
}