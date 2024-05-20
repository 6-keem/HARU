package com.cookandroid.jlptvocabularyapplication.database.tableclass.word;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    public void insertWord(Word word);

    @Transaction
    @Query("SELECT * FROM word")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * FROM word WHERE level like :level ORDER BY RANDOM() LIMIT 20")
    List<Word> getWordForTest(String level);

    // kanji가 없는경우 furigana로 표시
    @Query("SELECT * FROM word " +
            "WHERE word_meaning != :meaning and length(kanji) != 0 " +
            "ORDER BY RANDOM() LIMIT :limit")
    List<Word> getWordForKanjiProblem(String meaning, int limit);

    // 부분 문자열 검색으로 중복된 것 나올 수 있음
    // kanji가 없을 경우 다른 문제 선택
    @Query("SELECT * FROM word " +
            "WHERE (kanji LIKE '%' || :kanjiToken || '%'  or furigana LIKE '%' || :furiganaToken || '%') and furigana != :furigana " +
            "ORDER BY RANDOM() LIMIT :limit")
    List<Word> getWordForMeaningProblem(String kanjiToken, String furiganaToken, String furigana, int limit);

    // 부분 문자열 검색으로 중복된 것 나올 수 있음
    @Query("SELECT furigana FROM word " +
            "WHERE furigana LIKE '%' || :furiganaToken and furigana != :furigana and length(kanji) != 0 " +
            "ORDER BY RANDOM() LIMIT :limit")
    List<String> getWordsFuriganaProblem(String furiganaToken, String furigana, int limit);
    @Query("SELECT furigana FROM word " +
            "WHERE furigana LIKE '%' || :furiganaToken and furigana != :furigana and length(kanji) == 0 " +
            "ORDER BY RANDOM() LIMIT :limit")
    List<String> getWordsFuriganaProblemWhenFurigana(String furiganaToken, String furigana, int limit);

    @Query("SELECT * FROM word WHERE level LIKE :level and word_id >= :begin and word_id < :end")
    List<Word> getWords(String level, int begin, int end);

    @Query("SELECT * FROM word WHERE level LIKE :level limit 1")
    int getLevelsFirstWordID(String level);

    @Query("SELECT count(*) FROM word WHERE level LIKE :level and star_count > :star_count")
    int getWordsCount(String level, int star_count );

    @Query("UPDATE word " +
            "SET bookmark = :status " +
            "WHERE word_id = :wordID")
    void updateBookmark(long wordID, boolean status);
}
