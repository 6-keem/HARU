package com.cookandroid.jlptvocabularyapplication;

import java.util.HashMap;

public class Constants {
    public static final HashMap<String, String> cityNameConvertor = new HashMap<String, String>(){{
        put("서울특별시","Seoul");
        put("부산광역시","Busan");
        put("대구광역시","Daegu");
        put("인천광역시","Incheon");
        put("광주광역시","Gwangju");
        put("대전광역시","Daejeon");
        put("울산광역시","Ulsan");
        put("경기도","Suwon-si");
        put("충청남도","Daejeon");
        put("충청북도","Cheongju-si");
        put("전라남도","Gwangju");
        put("전라북도","Jeonju");
        put("경상남도","Busan");
        put("경상북도","Daegu");
        put("강원도","Hongchon");
        put("제주도","Jeju");
    }};

    public static final int KANJI = 1;
    public static final int FURIGANA = 2;
    public static final int MEANING = 3;

}
