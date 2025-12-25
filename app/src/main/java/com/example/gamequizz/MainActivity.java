package com.example.gamequizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

// Cần import chính xác 2 dòng này vì Activity nằm trong thư mục ui
import com.example.gamequizz.ui.GameActivity;
import com.example.gamequizz.ui.LeaderboardActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ID từ XML
        Button btnStart = findViewById(R.id.btnStart);
        Button btnRank = findViewById(R.id.btnLeaderboard);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        btnRank.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });
    }
}