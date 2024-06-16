package com.cookandroid.haru.screens.studyactivity.carditem;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTextToSpeech {
    private static TextToSpeech textToSpeech = null;
    public static TextToSpeech getInstance(Context context){
        if(textToSpeech == null){
            textToSpeech = new TextToSpeech(context, status -> {
                if(status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.JAPAN);
                    textToSpeech.setSpeechRate(0.7f);
                }
            }, "com.google.android.tts");
        }
        return textToSpeech;
    }
}
