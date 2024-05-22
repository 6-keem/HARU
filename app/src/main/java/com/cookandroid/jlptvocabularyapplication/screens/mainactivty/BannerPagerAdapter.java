package com.cookandroid.jlptvocabularyapplication.screens.mainactivty;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.jlptvocabularyapplication.Constants;
import com.cookandroid.jlptvocabularyapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BannerPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int[] layouts;

    public BannerPagerAdapter(int[] layouts) { this.layouts = layouts; }

    @Override
    public int getItemViewType(int position) {
        return layouts[position % layouts.length];
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        if (viewType == R.layout.banner_1)
            return new FirstBannerViewHolder(view);
        else if (viewType == R.layout.banner_2)
            return new SecondBannerViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FirstBannerViewHolder)
            ((FirstBannerViewHolder) holder).onBind();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class FirstBannerViewHolder extends RecyclerView.ViewHolder {
        TextView dDayTextView;
        FirstBannerViewHolder(View itemView) {
            super(itemView);
            TextView textView = (TextView) itemView.findViewById(R.id.link_banner);
            dDayTextView = (TextView) itemView.findViewById(R.id.dday);
            textView.setOnClickListener(v-> {
                String link = "https://www.jlpt.or.kr/html/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, itemView.getContext().getPackageName());
                itemView.getContext().startActivity(intent);
            });
        }
        void onBind(){
            Calendar now = Calendar.getInstance();
            long time = now.getTimeInMillis();
            DateFormat format = new SimpleDateFormat("yyyyMMdd");

            long days = 0;
            try {
                Date d1 = format.parse(Constants.testDate);
                long Sec = (d1.getTime() - time) / 1000;
                days = Sec / (24*60*60);
            } catch (ParseException ignore) { }
            String dDay = days == 0 ? "D-Day" : ("D-"+days);
            dDayTextView.setText(dDay);
        }
    }

    public class SecondBannerViewHolder extends RecyclerView.ViewHolder {
        SecondBannerViewHolder(View itemView) {
            super(itemView);
            TextView textView = (TextView) itemView.findViewById(R.id.link_banner);

            textView.setOnClickListener(v-> {
                String link = "https://japan.siwonschool.com/?s=event&p=end_pkg";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, itemView.getContext().getPackageName());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}