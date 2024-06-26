package com.cookandroid.haru.database.tableclass.word;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cookandroid.haru.database.converters.SentenceConverter;
import com.cookandroid.haru.database.converters.WordMeaningConverter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "word")
public class Word implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    public long wordId = 0;

    @ColumnInfo(name = "link")
    @SerializedName("link") public String link;
    @ColumnInfo(name = "word_class")
    @SerializedName("word_class") public String wordClass;
    @ColumnInfo(name = "star_count")
    @SerializedName("star_count") public int starCount;
    @ColumnInfo(name = "word_meaning")
    @TypeConverters(WordMeaningConverter.class)
    @SerializedName("word_meaning")     public List<String> wordMeaning;

    @ColumnInfo(name = "kanji")
    @SerializedName("kanji")    public String kanji;

    @ColumnInfo(name = "furigana")
    @SerializedName("furigana")     public String furigana;

    @ColumnInfo(name = "sentences")
    @TypeConverters(SentenceConverter.class)
    @SerializedName("sentences") public List<Sentence> sentences;

    @ColumnInfo(name = "level")
    @SerializedName("level")     public int level;

    @ColumnInfo(name = "bookmark", defaultValue = "false")
    @SerializedName("bookmark") public boolean bookmark;

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWordClass() {
        return wordClass;
    }

    public void setWordClass(String wordClass) {
        this.wordClass = wordClass;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public List<String> getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(List<String> wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getFurigana() {
        return furigana;
    }

    public void setFurigana(String furigana) {
        this.furigana = furigana;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
    @Override
    public String toString() {
        return "Word{" +
                "link='" + link + '\'' +
                ", word_class='" + wordClass + '\'' +
                ", star_count=" + starCount +
                ", wd_meaning=" + wordMeaning +
                ", kanji='" + kanji + '\'' +
                ", furigana='" + furigana + '\'' +
                ", sentences=" + sentences +
                '}';
    }
}