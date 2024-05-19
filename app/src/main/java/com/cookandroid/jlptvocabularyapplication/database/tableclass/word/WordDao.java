package com.cookandroid.jlptvocabularyapplication.database.tableclass.word;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

/*
            https://developer.android.com/training/data-storage/room/accessing-data?hl=ko#java
            쿼리문에 변수 사용
            @Query("SELECT * FROM word WEHRE word_id > :start and word_id < :end")
            public List<Word> getWordsCustom(int start, int end)


            // 발음 비슷한 거 랜덤해서 4개 가져옴 furigana 파싱 필요
            @Query("SELECT *
                    FROM word
                    WEHRE furigana like :furigana and ORDER BY RAND() LIMIT 4")
            public List<Word> getWordsCustom(String furigana)
 */



@Dao
public interface WordDao {
    @Insert
    public void insertWord(Word word);

    @Transaction
    @Query("SELECT * FROM word")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM word WHERE level = :level and word_id >= :begin and word_id < :end")
    List<Word> getWords(int level, int begin, int end);

    @Query("SELECT * FROM word WHERE level = :level limit 1")
    int getLevelsFirstWordID(int level);

    @Query("SELECT count(*) FROM word WHERE level = :level and star_count > :star_count")
    int getWordsCount(int level, int star_count );
}
