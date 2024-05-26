package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.userdata.UserDataDao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalyticsPieChart extends Fragment {
    private View view;
    private int[] colors = {R.color.graph_color_level_5, R.color.graph_color_level_4, R.color.graph_color_level_3,R.color.graph_color_level_2,R.color.graph_color_level_1,R.color.foreground_color};
    private String[] labels = {"N5", "N4","N3","N2","N1","ALL"};
    private ArrayList<Integer> colorArrayList = new ArrayList<>();
    private HashMap<String,Pair<Float, Float>> unitInfos = new HashMap<>();
    private ArrayList<PieEntry> pieEntries;
    private float studiedTotal = 0, total;
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
        setTextView((int)((studiedTotal * 100)/total), (int)studiedTotal, (int)total);
    }

    private void setPieChart(View view){
        pieEntries = setData();

        ArrayList<Integer> pieColors = new ArrayList<>();
        for(Integer color : colorArrayList)
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
        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTouchEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateY(1200, Easing.EaseInOutCubic);
        pieChart.animate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry p = (PieEntry) e;
                for(int i = 0 ; i < pieEntries.size() ; i ++){
                    if(p.equals(pieEntries.get(i))){
                        int index = 0;
                        for(int j = 0 ; j < labels.length ; j ++){
                            if(p.getLabel().equals(labels[j])) {
                                index = j;
                                break;
                            }
                        }

                        Pair<Float, Float> unitinfo = unitInfos.get(p.getLabel());
                        setTextView((int)(unitinfo.first * 100 / unitinfo.second),
                                Math.round(unitinfo.first), Math.round(unitinfo.second));
                        TextView level = (TextView) view.findViewById(R.id.pie_level);
                        level.setText(labels[index]);
                        level.setTextColor(getResources().getColor(colors[index]));
                    }
                }
            }
            @Override
            public void onNothingSelected() {
                setTextView((int)((studiedTotal * 100)/total), (int)studiedTotal, (int)total);
            }
        });

        pieChart.invalidate();
    }

    private ArrayList<PieEntry> setData(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        UserDataDao userDataDao = WordsDatabase.getInstance(getContext()).userDataDao();
        float percent = 0;
        total = userDataDao.getAllWordsCount();
        for(int i = 5 ; i >= 1 ; i--){
            UserData userData = userDataDao.getChapterData(Integer.toString(i),0);
            if(userData.getStudiedCount() != 0) {
                float currentStudiedTotal = (float)userData.getStudiedCount() * 100 / total;
                pieEntries.add(new PieEntry(currentStudiedTotal,labels[5-i]));
                studiedTotal += userData.getStudiedCount();
                percent += currentStudiedTotal;
                colorArrayList.add(colors[5-i]);
                unitInfos.put(labels[5-i], new Pair<>(userData.getStudiedCount()*1.0f,userData.getTotal()*1.0f));
            }
        }

        if(percent != 100) {
            pieEntries.add(new PieEntry(100 - percent,labels[5]));
            colorArrayList.add(colors[5]);
            unitInfos.put(labels[5],new Pair<>(studiedTotal,total));
        }
        return pieEntries;
    }


    private void setTextView(int percentage, int studiedTotal, int wordtotal){
        TextView percentageTextView = (TextView) view.findViewById(R.id.total_percentage);
        TextView studiedTextView = (TextView) view.findViewById(R.id.done);
        TextView totalTextView = (TextView) view.findViewById(R.id.total_word);

        percentageTextView.setText(percentage + "%");
        studiedTextView.setText(""+studiedTotal);
        totalTextView.setText(""+wordtotal);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        colors = null;
        labels = null;
        colorArrayList = null;
        pieEntries = null;
        unitInfos = null;
    }
}
