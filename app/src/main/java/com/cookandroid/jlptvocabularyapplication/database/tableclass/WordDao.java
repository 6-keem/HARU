package com.cookandroid.jlptvocabularyapplication.database.tableclass;

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

    @Query("SELECT * FROM word WHERE star_count > 0 and word_id > 0 and word_id < 100")
    List<Word> getWords();
}