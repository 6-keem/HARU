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

import com.cookandroid.jlptvocabularyapplication.R;

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
            return new BannerOneViewHolder(view);
        else if (viewType == R.layout.banner_2)
            return new BannerTwoViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class BannerOneViewHolder extends RecyclerView.ViewHolder {
        BannerOneViewHolder(View itemView) {
            super(itemView);
            TextView textView = (TextView) itemView.findViewById(R.id.link_banner);
            textView.setOnClickListener(v-> {
                String link = "https://www.jlpt.or.kr/html/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, itemView.getContext().getPackageName());
                itemView.getContext().startActivity(intent);
            });
        }

    }

    public class BannerTwoViewHolder extends RecyclerView.ViewHolder {
        BannerTwoViewHolder(View itemView) {
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