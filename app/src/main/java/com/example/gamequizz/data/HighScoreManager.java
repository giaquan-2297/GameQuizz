package com.example.gamequizz.data;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreManager {
    private static final String PREFS_NAME = "GamePrefs";
    private static final String KEY_SCORES = "HighScores";

    public static void saveScore(Context context, String name, int score) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currentData = prefs.getString(KEY_SCORES, "");
        List<String> scoreList = new ArrayList<>();

        if (!currentData.isEmpty()) {
            Collections.addAll(scoreList, currentData.split("\\|"));
        }
        scoreList.add(name + ": " + score);

        Collections.sort(scoreList, (s1, s2) -> {
            int sc1 = Integer.parseInt(s1.split(": ")[1]);
            int sc2 = Integer.parseInt(s2.split(": ")[1]);
            return Integer.compare(sc2, sc1);
        });

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(scoreList.size(), 5); i++) {
            sb.append(scoreList.get(i)).append("|");
        }
        prefs.edit().putString(KEY_SCORES, sb.toString()).apply();
    }

    public static List<String> getTopScores(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String data = prefs.getString(KEY_SCORES, "");
        List<String> list = new ArrayList<>();
        if (!data.isEmpty()) {
            Collections.addAll(list, data.split("\\|"));
        }
        return list;
    }
}