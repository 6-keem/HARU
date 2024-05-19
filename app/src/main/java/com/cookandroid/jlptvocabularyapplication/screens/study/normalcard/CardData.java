package com.cookandroid.jlptvocabularyapplication.screens.study.normalcard;

import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Sentence;
import com.cookandroid.jlptvocabularyapplication.database.tableclass.word.Word;

import java.util.List;

public class CardData{
    private String kanji, japanese, wordClass, korean, furigana, link;
    private List<Sentence> sentences;
    private String[] otherJapanese;
    private List<String> wordMeaning;
    private int starCount;
    public CardData(Word word){
        if(word.kanji.length() != 0) {
            otherJapanese = word.getKanji().split("·");
            kanji = japanese = otherJapanese[0];
            furigana = word.getFurigana();
        } else {
            furigana = japanese = word.getFurigana();
            kanji = null;
        }
        sentences = word.getSentences();
        wordClass = word.getWordClass();
        link = word.getLink();
        starCount = word.getStarCount();
        wordMeaning = word.getWordMeaning();
    }

    public String getKanji() {
        return kanji;
    }

    public String getJapanese() {
        return japanese;
    }

    public String getWordClass() {
        return wordClass;
    }

    public String getKorean() {
        return korean;
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
