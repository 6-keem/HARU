package com.cookandroid.jlptvocabularyapplication.database;

public class Sentence {
    private String jp;
    private String kr;
    public Sentence(String key, String value){
        this.jp = key;
        this.kr = value;
    }

    public String getKr() {
        return kr;
    }

    public void setKr(String kr) {
        this.kr = kr;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }
    @Override
    public String toString() {
        return "Sentence{" +
                "sentence='" + jp + '\'' +
                ", st_meaning='" + kr + '\'' +
                '}';
    }
}
