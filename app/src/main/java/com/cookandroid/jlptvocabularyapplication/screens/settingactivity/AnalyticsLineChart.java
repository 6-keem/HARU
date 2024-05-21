package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

public class AnalyticsLineChart extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.setting_linechart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDropDownMenu(view);
        setLineChart();
    }
    private void setDropDownMenu(View view) {
        String[] stringArray = getResources().getStringArray(R.array.dropdwon_analytics);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, stringArray);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private void setLineChart() {
        ArrayList<Entry> entryArrayList= new ArrayList<>();
        entryArrayList.add(new Entry(1f,8f));
        entryArrayList.add(new Entry(2f,5f));
        entryArrayList.add(new Entry(3f,2f));
        entryArrayList.add(new Entry(4f,9f));

        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "");
        Integer lineColor = view.getResources().getColor(R.color.graph_color_coral_blue);
        lineDataSet.setColor(lineColor);
        lineDataSet.setCircleColor(lineColor);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.linechart_gradient));
        lineDataSet.setValueTextColor(requireContext().getResources().getColor(R.color.foreground_color));
        lineDataSet.setValueTextSize(14f);

        LineChart lineChart = (LineChart) view.findViewById(R.id.linechart);
        lineChart.setData(new LineData(lineDataSet));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(view.getResources().getColor(R.color.foreground_color_gray));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new WeekFormatter());
        xAxis.setSpaceMax(0.2f);
        xAxis.setSpaceMin(0.2f);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(view.getResources().getColor(R.color.card_color));

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawLabels(false);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setGridColor(view.getResources().getColor(R.color.foreground_color_gray));
        axisLeft.setGridLineWidth(1f);
        axisLeft.setLabelCount(4);

        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawLabels(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setLabelCount(4);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setXOffset(-50f);
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawMarkers(true);
        lineChart.animateY(1200);
    }


    private class WeekFormatter extends ValueFormatter{
        private Format format = new DecimalFormat("#주");
        @Override
        public String getFormattedValue(float value) {
            return format.format(value);
        }
    }
}
