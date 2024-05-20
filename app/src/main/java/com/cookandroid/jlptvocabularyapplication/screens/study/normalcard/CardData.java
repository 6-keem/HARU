package com.cookandroid.jlptvocabularyapplication.screens.study.normalcard;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Sentence;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;

import java.util.List;

public class CardData{
    private long wordID;
    private String kanji, japanese, wordClass, furigana, link;
    private List<Sentence> sentences;
    private String[] otherJapanese;
    private List<String> wordMeaning;
    private int starCount;
    private boolean isBookmarked;
    public CardData(Word word){
        if(word.kanji.length() != 0) {
            otherJapanese = word.getKanji().split("·");
            kanji = japanese = otherJapanese[0];
            furigana = word.getFurigana();
        } else {
            furigana = japanese = word.getFurigana();
            kanji = null;
        }
        wordID = word.getWordId();
        sentences = word.getSentences();
        wordClass = word.getWordClass();
        link = word.getLink();
        starCount = word.getStarCount();
        wordMeaning = word.getWordMeaning();
        isBookmarked = word.isBookmark();
    }

    public long getWordID() {
        return wordID;
    }

    public boolean isBookmarked() { return isBookmarked; }

    public void setBookmarked(boolean bookmarked){this.isBookmarked = bookmarked;}
    public String getKanji() {
        return kanji;
    }

    public String getJapanese() {
        return japanese;
    }

    public String getWordClass() {
        return wordClass;
    }

    public String getFurigana() {
        return furigana;
    }

    public String getLink() {
        return link;
    }

    public String[] getOtherJapanese() {
        return otherJapanese;
    }

    public List<String> getWordMeaning() {
        return wordMeaning;
    }

    public int getStarCount() {
        return starCount;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
}
