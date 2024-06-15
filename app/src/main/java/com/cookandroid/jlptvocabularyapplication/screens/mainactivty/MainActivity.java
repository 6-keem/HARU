package com.cookandroid.jlptvocabularyapplication.screens.mainactivty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.locationcoord.LocationCoord;
import com.cookandroid.jlptvocabularyapplication.screens.mainactivty.chapter.ChapterFragment;
import com.cookandroid.jlptvocabularyapplication.screens.mainactivty.level.LevelRecyclerViewAdapter;
import com.cookandroid.jlptvocabularyapplication.screens.settingactivity.SettingActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<ChapterFragment> fragmentArrayList = new ArrayList<>();
    private Handler sliderHandler = new Handler();

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private ViewPager2 viewPager;
    private int current = 0;
    private boolean isAutoSlideEnabled = true;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setToolbar();

        RecyclerView recyclerView = (RecyclerView)  findViewById(R.id.level_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        ArrayList<String> levelItems = setLevelItem();
        LevelRecyclerViewAdapter levelRecyclerViewAdapter = new LevelRecyclerViewAdapter(this, levelItems);
        recyclerView.setAdapter(levelRecyclerViewAdapter);

        setChapterFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, fragmentArrayList.get(0));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        setWeatherIcon();
        setHeader();
        setViewPager();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        fragmentArrayList.get(current).onResume();
    }

    private void setWeatherIcon(){
        ImageView weatherIcon = (ImageView) findViewById(R.id.weather_icon);
        // TODO: 2024-06-14 권한확인 
//        if (!(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
//            weatherIcon.setVisibility(View.INVISIBLE);
//            return;
//        }
        new Thread(() -> {
            int count = 0;
            LocationCoord locationCoord = LocationCoord.getInstance();
            while(!locationCoord.getWeatherStatus()){
                if(count == 60)
                    return;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count ++;
            }
            String drawableName = "_" + LocationCoord.getIconString();
            mainHandler.post(() -> {
                Log.d("USER","실행됐음");
                int drawableId = getApplicationContext().getResources().getIdentifier(drawableName, "drawable", getApplicationContext().getPackageName());
                weatherIcon.setImageResource(drawableId);
            });
        }).start();
    }
    private void setHeader() {
        Calendar now = Calendar.getInstance();
        long time = now.getTimeInMillis();
        Date date = new Date(time);
        int index = now.get(Calendar.DAY_OF_WEEK);

        String[] weeks = {"일","월","화","수","목","금","토"};
        TextView textView = (TextView) findViewById(R.id.timetext);
        TextView textView1 = (TextView)findViewById(R.id.greetingtext);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        textView.setText(simpleDateFormat.format(date) + " " + weeks[index-1] + "요일");
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if(6 <= hour && hour < 12)
            textView1.setText("좋은 아침이에요");
        else if(12 <= hour && hour < 18 )
            textView1.setText("좋은 오후에요");
        else
            textView1.setText("좋은 저녁이에요");
    }
    private void setToolbar(){
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.main_toolbar);
        ImageButton imageButton = (ImageButton)findViewById(R.id.dashboard_icon);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SettingActivity.class).setAction("Dashboard");
            startActivity(intent);
        });
        setSupportActionBar(toolbar);
    }
    private ArrayList<String> setLevelItem(){
        ArrayList<String> levelItems = new ArrayList<>();
        levelItems.add("ALL");
        levelItems.add("N5");
        levelItems.add("N4");
        levelItems.add("N3");
        levelItems.add("N2");
        levelItems.add("N1");
        return levelItems;
    }

    private void setChapterFragments(){
        fragmentArrayList.add(new ChapterFragment(getApplicationContext(),0));
        for(int i = 5 ; i >= 1 ; i--)
            fragmentArrayList.add(new ChapterFragment(getApplicationContext(), i));
    }

    public void changeFragment(int level){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        current = level;
        fragmentTransaction.replace(R.id.fragment_layout, fragmentArrayList.get(level));
        fragmentTransaction.commit();
    }

    private void setViewPager() {
        viewPager = findViewById(R.id.bannerLayout);
        int[]layouts = {R.layout.banner_1, R.layout.banner_2};

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(layouts);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if(state == ViewPager2.SCROLL_STATE_DRAGGING)
                    stopAutoSlide();
                else if (state == ViewPager2.SCROLL_STATE_IDLE && !isAutoSlideEnabled)
                    startAutoSlide();
            }
        });
    }

    private void startAutoSlide() {
        isAutoSlideEnabled = true;
        sliderHandler.postDelayed(slideRunnable, 3000); // 3초마다 페이지 변경
    }

    private void stopAutoSlide() {
        isAutoSlideEnabled = false;
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoSlide();
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            sliderHandler.postDelayed(this, 5000);
        }
    };


}