package com.cookandroid.jlptvocabularyapplication.database.tableclass.word;

public class Sentence {
    private String jpSentence;
    private String krSentence;
    public Sentence(String japaneseSentence, String koreanSentence){
        this.jpSentence = japaneseSentence;
        this.krSentence = koreanSentence;
    }

    public String getJpSentence() {
        return jpSentence;
    }

    public void setJpSentence(String jpSentence) {
        this.jpSentence = jpSentence;
    }

    public String getKrSentence() {
        return krSentence;
    }

    public void setKrSentence(String krSentence) {
        this.krSentence = krSentence;
    }
}
