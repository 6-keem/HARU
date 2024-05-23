package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.WordsDatabase;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyData;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.studydata.StudyDataDao;

import java.util.Calendar;
import java.util.List;

public class StreakFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.setting_streak, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] box = {
                R.id.box1, R.id.box2, R.id.box3,R.id.box4,
                R.id.box5, R.id.box6, R.id.box7,R.id.box8,
                R.id.box9, R.id.box10, R.id.box11,R.id.box12,
                R.id.box13, R.id.box14, R.id.box15,R.id.box16,
                R.id.box17, R.id.box18, R.id.box19,R.id.box20,
        };

        int [][]year = new int[10][365];
        StudyDataDao dataDao = WordsDatabase.getInstance(requireContext()).studyDataDao();
        List<StudyData> studyDataList = dataDao.getAllStudyData();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        for(StudyData studyData : studyDataList){
            calendar.setTimeInMillis(studyData.getDate());
            int savedYear = calendar.get(Calendar.YEAR);
            int savedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            if(nowYear - savedYear > 4)
                break;
            year[nowYear-savedYear][nowDayOfYear-savedDayOfYear]++;
        }

        boolean flag = true;
        int count = 0;
            for(int i = 0 ; i < year.length ; i++){
            for(int j = 0 ; j < year[i].length ; j ++){
                if(year[i][j] != 0){
                    if (flag)
                        count++;
                }
                else
                    flag = false;

                if(i == 0 && j < 20){
                    int drawbleID;
                    View boxView = (View) view.findViewById(box[j]);
                    switch(year[i][j]){
                        case 0:
                            drawbleID = R.drawable.streak_shadow_normal;
                            break;
                        case 1:
                            drawbleID = R.drawable.streak_shadow_one;
                            break;
                        case 2:
                            drawbleID = R.drawable.streak_shadow_two;
                            break;
                        case 3:
                            drawbleID = R.drawable.streak_shadow_three;
                            break;
                        default:
                            drawbleID = R.drawable.streak_shadow_four;
                            break;
                    }
                    boxView.setBackground(requireContext().getResources().getDrawable(drawbleID));
                }
            }
        }

        TextView textview = (TextView) view.findViewById(R.id.streak_count);
        textview.setText(""+count);
    }
}
