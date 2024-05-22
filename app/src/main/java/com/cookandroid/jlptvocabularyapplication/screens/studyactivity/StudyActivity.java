package com.cookandroid.jlptvocabularyapplication.screens.studyactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.CustomExitDialog;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.PieChartDialog;
import com.cookandroid.jlptvocabularyapplication.screens.dialog.StudyPieChart;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.CardFragment;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.CardPagerAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.studyactivity.carditem.MyTextToSpeech;

import java.util.ArrayList;


@SuppressLint("SetTextI18n")
public abstract class StudyActivity extends AppCompatActivity {
    protected ArrayList<CardFragment> arrayList = new ArrayList<>();
    protected CardPagerAdapter cardPagerAdapter = null;
    protected ViewPager2 viewPager = null;
    protected ProgressBar progressBar = null;
    protected int level, position, wordEnd, retryCount, currentPage = 0;
    protected int checkCount = 0;
    protected TextView currentCount = null;
    protected Chronometer chronometer = null;
    private CustomExitDialog customExitDialog;
    protected PieChartDialog pieChartDialog;
    private View.OnClickListener mConfirmListener = view -> finish();
    private View.OnClickListener mCancelListener = view -> customExitDialog.dismiss();
    protected View.OnClickListener dialogConfrimListener = view -> finish();

    abstract protected void setCardItem();
    abstract protected void onExit(int factor);
    protected abstract String setToolbarTitle();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study);
        customExitDialog = new CustomExitDialog(StudyActivity.this, mCancelListener, mConfirmListener);

        MyTextToSpeech.getInstance(getApplicationContext());
        Intent intent = getIntent();
        try {
            level = intent.getExtras().getInt("level");
            position = intent.getExtras().getInt("position");
        } catch (NullPointerException ignore) { }
        setCardItem();
        cardPagerAdapter = new CardPagerAdapter(this, arrayList);
        viewPager = (ViewPager2) findViewById(R.id.card_list);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(cardPagerAdapter);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(8));

        transformer.addTransformer( (page, position) -> {
                float v = 1-Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
        });
        setToolBar();
        setWidgets();
        setPageScrollEvent();
    }

    protected void setPageScrollEvent() {
        for (int i = 0 ; i < arrayList.size() ; i ++){
            CardFragment cardFragment = arrayList.get(i);
            cardFragment.setCustomOnClickListener(view -> toNextPage());
            cardFragment.setCustomCheckButtonOnClickListener(view -> checkCount++ );
        }
    }

    protected void toNextPage() {
        if(currentPage == wordEnd - 1){
            chronometer.stop();
            onExit(1);
            pieChartDialog = new StudyPieChart(StudyActivity.this, wordEnd-checkCount,
                    wordEnd, level, position, chronometer.getText().toString(), dialogConfrimListener);
            pieChartDialog.show();
        }
        else {
            viewPager.setCurrentItem(++currentPage, true);
            progressBar.setProgress(currentPage + 1);
            currentCount.setText(Integer.toString(currentPage + 1));
        }
    }


    private void setWidgets() {
        // TODO: 2024-05-17 DB 정보 가져와서 초기화
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgress(currentPage+1);
        progressBar.setMax(wordEnd);

        currentCount = (TextView) findViewById(R.id.count);
        TextView total = (TextView)findViewById(R.id.total_count);
        currentCount.setText(Integer.toString(currentPage+1));
        total.setText("/" + wordEnd);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void setToolBar() {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        ImageButton imageButton = (ImageButton) findViewById(R.id.back_arrow);
        String title = setToolbarTitle();
        toolbarTitle.setText(title);
        imageButton.setOnClickListener(v -> {
            customExitDialog.show();
        });
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.setAdapter(null);
        arrayList = null;
        cardPagerAdapter = null;
        if(customExitDialog != null && customExitDialog.isShowing())
            customExitDialog.dismiss();
        if(pieChartDialog != null && pieChartDialog.isShowing())
            pieChartDialog.dismiss();
        dialogConfrimListener = mCancelListener = mConfirmListener = null;
    }
}