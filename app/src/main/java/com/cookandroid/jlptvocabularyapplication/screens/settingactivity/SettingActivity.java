package com.cookandroid.jlptvocabularyapplication.screens.settingactivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.jlptvocabularyapplication.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportFragmentManager().beginTransaction().replace(R.id.analytics, new AnalyticsLineChart()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.piechartLayout, new AnalyticsPieChart()).commit();
        setToolbar();
    }

    private void setToolbar(){
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        ImageButton imageButton = (ImageButton) findViewById(R.id.back_arrow);
        toolbarTitle.setText("Dashboard");
        imageButton.setOnClickListener(v -> finish());
        setSupportActionBar(toolbar);
    }
}
