package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AnalyticsPieChart extends Fragment {
    private View view;
    private int[] colors = {R.color.graph_color_level_5, R.color.graph_color_level_4, R.color.graph_color_level_3,R.color.graph_color_level_2,R.color.graph_color_level_1,R.color.foreground_color};
    private String[] labels = {"N5", "N4","N3","N2","N1","ALL"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.setting_piechart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPieChart(view);
    }

    private void setPieChart(View view){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(12f, labels[0]));
        pieEntries.add(new PieEntry(8f, labels[1]));
        pieEntries.add(new PieEntry(15f, labels[2]));
        pieEntries.add(new PieEntry(9f, labels[3]));
        pieEntries.add(new PieEntry(0, labels[4]));
        pieEntries.add(new PieEntry(53f, labels[5]));

        ArrayList<Integer> pieColors = new ArrayList<>();
        for(Integer color : colors)
            pieColors.add(getContext().getResources().getColor(color));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(pieColors);
        pieDataSet.setDrawValues(false);

        PieChart pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.background_color));
        pieChart.setEntryLabelTypeface(Typeface.create("SANS", Typeface.BOLD));
        pieChart.setEntryLabelTextSize(11f);
        pieChart.getLegend().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTouchEnabled(false);
        pieChart.animateY(1200, Easing.EaseInOutCubic);
        pieChart.animate();
        pieChart.invalidate();
    }
}
