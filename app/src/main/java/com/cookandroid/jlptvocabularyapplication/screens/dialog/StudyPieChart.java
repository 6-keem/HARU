package com.cookandroid.jlptvocabularyapplication.screens.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.jlptvocabularyapplication.R;

public class StudyPieChart extends PieChartDialog {
    public StudyPieChart(Context context, int value, int total, int level, int chapter, String timeStamp, View.OnClickListener mConfirmListener){
        super(context, mConfirmListener);
        this.value = value;
        this.total = total;
        this.level = level;
        this.chapter = chapter;
        this.timeStamp = timeStamp;
    }

    @Override
    protected void setText() {
        TextView valueText = (TextView) findViewById(R.id.value);
        TextView totalText = (TextView) findViewById(R.id.wrong);

        valueText.setText("외운 단어 : " + value);
        totalText.setText("확인한 단어 : " + (total - value));
    }
}
