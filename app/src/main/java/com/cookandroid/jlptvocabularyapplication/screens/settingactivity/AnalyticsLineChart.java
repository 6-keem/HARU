package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyDataDao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AnalyticsLineChart extends Fragment {
    private View view;
    private List<StudyData> studyDataList = null;
    private int[] colors = {R.color.graph_color_level_5, R.color.graph_color_level_4, R.color.graph_color_level_3,R.color.graph_color_level_2,R.color.graph_color_level_1,R.color.foreground_color};
    private String[] labels = {"N5", "N4","N3","N2","N1","ALL"};
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
        getData();
        setDropDownMenu(view);
        setMultiLineChart();
    }
    private void setDropDownMenu(View view) {
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.requireContext(), R.array.dropdwon_analytics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    setLineChart();
                else
                    setMultiLineChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setLineChart();
            }
        });
    }

    private void setLineChart() {

        ArrayList<Entry> entryArrayList = setStudyTimeData();

        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "");
        Integer lineColor = view.getResources().getColor(R.color.graph_color_coral_blue);
        lineDataSet.setColor(lineColor);
        lineDataSet.setCircleColor(lineColor);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setValueFormatter(new MinuteFormatter());
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.linechart_gradient));
        lineDataSet.setHighLightColor(requireContext().getResources().getColor(R.color.foreground_color));
        lineDataSet.setHighlightLineWidth(1f);
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
        lineChart.getLegend().setTextColor(view.getResources().getColor(R.color.foreground_color));
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setXOffset(-50f);
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawMarkers(true);
        lineChart.animateY(1200);
    }

    private void setMultiLineChart() {
        ArrayList<ArrayList<Entry>> entryArrayList = setCorrectAverageData();

        LineData lineData = new LineData();
        for(int i = 0 ; i < entryArrayList.size() ; i ++){
            LineDataSet lineDataSet = new LineDataSet(entryArrayList.get(i), labels[5-i]);
            int lineColor = view.getResources().getColor(colors[5-i]);
            lineDataSet.setColor(lineColor);
            lineDataSet.setCircleColor(lineColor);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.setValueFormatter(new PercentValueFormatter());
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighlightLineWidth(0f);
            lineDataSet.setHighLightColor(requireContext().getResources().getColor(R.color.foreground_color));
            lineDataSet.setValueTextColor(requireContext().getResources().getColor(R.color.foreground_color));
            lineDataSet.setValueTextSize(14f);
            lineDataSet.setLineWidth(2f);
            lineData.addDataSet(lineDataSet);
        }

        LineChart lineChart = (LineChart) view.findViewById(R.id.linechart);
        lineChart.setData(lineData);

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
        lineChart.getLegend().setTextColor(view.getResources().getColor(R.color.foreground_color));
        lineChart.getLegend().setXOffset(10f);
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawMarkers(true);
        lineChart.animateY(1200);
    }
    private ArrayList<ArrayList<Entry>> setCorrectAverageData(){
        if(studyDataList == null || studyDataList.size() == 0)
            return null;

        ArrayList<ArrayList<Entry>> entryArraylist = new ArrayList<>();
        for(int i = 0 ; i < 6 ; i ++)
            entryArraylist.add(new ArrayList<>());

        double[][] matrix = new double[6][4];
        long [][]count = new long[6][4];
        long weekDelta = MyConverter.millisecToWeek(studyDataList.get(0).getDate());
        for(StudyData studyData : studyDataList){
            if(!studyData.getOption().equals("TEST"))
                continue;

            int week = MyConverter.millisecToWeek(studyData.getDate());
            if(weekDelta - week > 3)
                break;

            matrix[studyData.getLevel()][(int)weekDelta-week] += (double)(studyData.getWrong()) * 100/ studyData.getTotal();
            count[studyData.getLevel()][(int)weekDelta-week]++;
        }

        for(int i = 0 ; i < matrix.length ; i ++){
            for(int j = matrix[i].length-1 ; j >= 0 ; j--){
                ArrayList arrayList = entryArraylist.get(i);
                if(count[i][j] != 0) {
                    matrix[i][j] /= count[i][j];
                }
                arrayList.add(new Entry((float)(3-j+1), (int)matrix[i][j]));
            }
        }
        return entryArraylist;
    }
    private ArrayList<Entry> setStudyTimeData(){
        if(studyDataList == null || studyDataList.size() == 0)
            return null;

        ArrayList<Entry> entryArrayList = new ArrayList<>();
        long []times = {0,0,0,0};
        int []count = {0,0,0,0};
        int index = 0;
        long weekDelta = MyConverter.millisecToWeek(studyDataList.get(0).getDate());
        for(StudyData studyData : studyDataList){
            int week = MyConverter.millisecToWeek(studyData.getDate());
            if(weekDelta - week > 3)
                break;
            times[(int) (weekDelta-week)] += studyData.getTime();
            count[(int) (weekDelta-week)]++;
        }

        for(int i = times.length-1 ; i >= 0 ; i--) {
            if(count[i]!=0)
                times[i] /= count[i];
            entryArrayList.add(new Entry((float) (3 - i + 1), (float) (times[i] / (1000.0 * 60))));
        }
        return entryArrayList;
    }

    private void getData(){
        StudyDataDao studyDataDao = WordsDatabase.getInstance(getContext()).studyDataDao();
        studyDataList =  studyDataDao.getAllStudyData();
    }

    private static class MyConverter {
        public static Pair<Integer, Integer> millisecToTime(long millis){
            long seconds = millis / 1000;
            long remainingMilliseconds = millis % 1000;
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;
            if(hours > 0)
                return new Pair<>((int)minutes, (int)remainingMilliseconds);
            return new Pair<>((int)remainingMinutes,(int)remainingSeconds);
        }
        public static int millisecToWeek(long millis){
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTimeInMillis(millis);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }
    }
    private class PercentValueFormatter extends ValueFormatter {
        private Format format = new DecimalFormat("#%");
        @Override
        public String getFormattedValue(float value) {
            return format.format(value/100);
        }
    }
    private class MinuteFormatter extends ValueFormatter {
        @SuppressLint("DefaultLocale")
        @Override
        public String getFormattedValue(float value) {
            return String.format("%.1f분",value);
        }
    }
    private class WeekFormatter extends ValueFormatter{
        private Format format = new DecimalFormat("#주");
        @Override
        public String getFormattedValue(float value) {
            return format.format(5-value);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        studyDataList = null;
    }
}
