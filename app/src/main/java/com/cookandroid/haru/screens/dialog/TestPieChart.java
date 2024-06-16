package com.cookandroid.haru.screens.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cookandroid.haru.R;

public class TestPieChart extends PieChartDialog {

    public TestPieChart(Context context, int value, int total, int level, int chapter, String timeStamp, View.OnClickListener mConfirmListener){
        super(context, mConfirmListener);
        this.value = total-value;
        this.total = total;
        this.level = level;
        this.chapter = chapter;
        this.timeStamp = timeStamp;
    }

    @Override
    protected void setText() {
        TextView valueText = (TextView) findViewById(R.id.value);
        TextView totalText = (TextView) findViewById(R.id.wrong);

        valueText.setText("맞힌 단어 : " + value);
        totalText.setText("틀린 단어 : " + (total - value));
    }
}
