package com.example.gamequizz.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gamequizz.R;
import com.example.gamequizz.data.HighScoreManager;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ListView lv = findViewById(R.id.lvLeaderboard);
        Button btnBack = findViewById(R.id.btnBackFromRank);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, HighScoreManager.getTopScores(this));
        lv.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish()); // Đóng màn hình này để quay về Main
    }
}