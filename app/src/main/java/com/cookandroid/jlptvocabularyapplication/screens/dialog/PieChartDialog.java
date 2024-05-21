package com.cookandroid.jlptvocabularyapplication.screens.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cookandroid.jlptvocabularyapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public abstract class PieChartDialog extends Dialog {
    private PieChart pieChart;
    protected int value, total, level, chapter;
    protected String timeStamp;
    protected Context context;
    protected View.OnClickListener mConfirmListener;
    public PieChartDialog(@NonNull Context context, View.OnClickListener mConfirmListener) {
        super(context);
        this.context = context;
        this.mConfirmListener = mConfirmListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialog();
        setPieChart();
    }

    private void setDialog(){
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        setCanceledOnTouchOutside(true);
        setCancelable(true);

        setContentView(R.layout.dialog_done);
        TextView averageText = (TextView) findViewById(R.id.average);
        TextView totalText = (TextView) findViewById(R.id.total);
        averageText.setText((value * 100 / total) + "%");
        totalText.setText("(" + value + "/" + total +")");

        TextView timeStampTextView = (TextView) findViewById(R.id.timeStamp);
        TextView confirm = (TextView) findViewById(R.id.confirm);
        confirm.setOnClickListener(mConfirmListener);
        timeStampTextView.setText(timeStamp);
        setText();
    }
    private void setPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        float piValue = (value * 100) / total;
        if(piValue != 0)
            pieEntries.add(new PieEntry(piValue));
        if(piValue != 100)
            pieEntries.add(new PieEntry(100 - total));


        ArrayList<Integer> pieColors = new ArrayList<Integer>();
        if(piValue != 0)
            pieColors.add(context.getResources().getColor(R.color.graph_color_coral_blue));
        if(piValue != 100)
            pieColors.add(context.getResources().getColor(R.color.foreground_color));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(pieColors);
        pieDataSet.setDrawValues(false);

        pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(60f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTouchEnabled(false);
        pieChart.animateY(1200, Easing.EaseInOutCubic);
        pieChart.animate();
        pieChart.invalidate();
    }

    protected abstract void setText();
}
