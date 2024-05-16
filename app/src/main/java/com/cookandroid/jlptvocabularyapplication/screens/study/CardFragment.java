package com.cookandroid.jlptvocabularyapplication.screens.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cookandroid.jlptvocabularyapplication.R;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.Word;
import com.cookandroid.jlptvocabularyapplication.screens.chapter.ChapterRecyclerViewAdapter;

public class CardFragment extends Fragment {
    private CardView mCardView;
    private CardData cardData;
    public CardFragment(Word word){
        this.cardData = new CardData(word);
    }

    private OnClickListener mListener = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_card_item, container, false);
        CardView mCardView = (CardView) view.findViewById(R.id.cardView);
        Button button = (Button) view.findViewById(R.id.skip);
        TextView textView = (TextView) view.findViewById(R.id.japanese);
        String japanese = cardData.getJapanese();
        textView.setText(japanese);
        if(japanese.length() > 4)
            textView.setTextSize(textView.getTextSize() / 3);
        button.setOnClickListener(v -> {
            if(v != null)
                mListener.onClick(v);
        });

        return view;
    }

    public interface OnClickListener {
        void onClick(View view);
    }
    public void setOnClickListener(OnClickListener listener){
        this.mListener = listener;
    }
}
