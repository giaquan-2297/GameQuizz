package com.example.gamequizz.ui;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.gamequizz.R;
import com.example.gamequizz.data.HighScoreManager;
import com.example.gamequizz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private TextView tvQuestion, tvScore, tvTimer;
    private Button[] btnOptions = new Button[4];
    private Button btnGameMenu;

    private List<Question> questionList;
    private int currentIdx = 0, score = 0;

    private CountDownTimer countDownTimer;
    private final long START_TIME_IN_MILLIS = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Ánh xạ View
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        btnOptions[0] = findViewById(R.id.btnOption1);
        btnOptions[1] = findViewById(R.id.btnOption2);
        btnOptions[2] = findViewById(R.id.btnOption3);
        btnOptions[3] = findViewById(R.id.btnOption4);
        btnGameMenu = findViewById(R.id.btnGameMenu);

        initData();
        showQuestion();

        for (int i = 0; i < 4; i++) {
            final int choice = i;
            btnOptions[i].setOnClickListener(v -> handleAnswer(choice));
        }

        btnGameMenu.setOnClickListener(v -> showGameMenu());
    }

    private void initData() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Thành phố nào là 'Thành phố sương mù'?", "Nha Trang", "Đà Lạt", "Sa Pa", "Hội An", 1));
        questionList.add(new Question("Sông nào dài nhất thế giới?", "Mê Kông", "Amazon", "Niên (Nile)", "Trường Giang", 2));
        questionList.add(new Question("Nước có diện tích lớn nhất thế giới?", "Trung Quốc", "Mỹ", "Canada", "Nga", 3));
        questionList.add(new Question("Everest nằm ở dãy núi nào?", "Andes", "Himalaya", "Rocky", "Al-pơ", 1));
        questionList.add(new Question("Quốc gia hình chiếc ủng?", "Ý", "Hy Lạp", "Tây Ban Nha", "Pháp", 0));
        questionList.add(new Question("Hành tinh gần Mặt Trời nhất?", "Trái Đất", "Sao Hỏa", "Sao Thủy", "Sao Kim", 2));
        questionList.add(new Question("Nhóm máu 'chuyên cho' là?", "A", "B", "AB", "O", 3));
        questionList.add(new Question("Động vật chạy nhanh nhất đất liền?", "Báo săn", "Sư tử", "Linh dương", "Ngựa vằn", 0));
        questionList.add(new Question("Kim loại lỏng ở nhiệt độ thường?", "Sắt", "Thủy ngân", "Đồng", "Nhôm", 1));
        questionList.add(new Question("Cơ quan nào lọc máu?", "Phổi", "Tim", "Gan", "Thận", 3));
        questionList.add(new Question("Vua cuối cùng của phong kiến VN?", "Minh Mạng", "Tự Đức", "Bảo Đại", "Hàm Nghi", 2));
        questionList.add(new Question("Ai phát minh bóng đèn điện?", "Einstein", "Newton", "Edison", "Tesla", 2));
        questionList.add(new Question("Leonardo da Vinci là người nước nào?", "Anh", "Pháp", "Đức", "Ý", 3));
        questionList.add(new Question("Truyện Kiều là của ai?", "Nguyễn Trãi", "Nguyễn Du", "Hồ Xuân Hương", "Đoàn Thị Điểm", 1));
        questionList.add(new Question("Vạn Lý Trường Thành ở đâu?", "Nhật Bản", "Hàn Quốc", "Trung Quốc", "Ấn Độ", 2));
        questionList.add(new Question("Môn thể thao vua là gì?", "Bóng rổ", "Bóng đá", "Quần vợt", "Cầu lông", 1));
        questionList.add(new Question("Trận bóng đá chính thức dài bao phút?", "60", "80", "90", "100", 2));
        questionList.add(new Question("Grand Slam trên sân cỏ?", "Wimbledon", "US Open", "French Open", "Aus Open", 0));
        questionList.add(new Question("Mạng xã hội lớn nhất thế giới?", "Twitter", "Instagram", "Facebook", "TikTok", 2));
        questionList.add(new Question("Iron Man tên thật là gì?", "Steve Rogers", "Bruce Banner", "Peter Parker", "Tony Stark", 3));
    }

    private void showQuestion() {
        if (currentIdx < questionList.size()) {
            Question q = questionList.get(currentIdx);
            tvQuestion.setText(q.getQuestionText());
            String[] options = q.getOptions();
            for (int i = 0; i < 4; i++) {
                btnOptions[i].setText(options[i]);
                // Reset màu về mặc định và cho phép bấm
                btnOptions[i].setBackgroundTintList(null);
                btnOptions[i].setEnabled(true);
            }
            startTimer();
        } else {
            endGame();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();
        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Thời gian: " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                handleAnswer(-1); // Gọi handleAnswer với giá trị -1 để tính là hết giờ (sai)
            }
        }.start();
    }

    private void handleAnswer(int selectedIdx) {
        if (countDownTimer != null) countDownTimer.cancel();

        // Vô hiệu hóa nút để tránh bấm nhiều lần
        for (Button btn : btnOptions) btn.setEnabled(false);

        int correctIdx = questionList.get(currentIdx).getCorrectOptionIndex();

        // Hiển thị đáp án đúng màu Xanh
        btnOptions[correctIdx].setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        if (selectedIdx == correctIdx) {
            score++;
            tvScore.setText("Điểm: " + score);
        } else if (selectedIdx != -1) {
            // Nếu chọn sai, hiển thị nút đã chọn màu Đỏ
            btnOptions[selectedIdx].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        // Chờ 1.5 giây để xem kết quả rồi sang câu tiếp theo
        new Handler().postDelayed(() -> {
            currentIdx++;
            showQuestion();
        }, 1500);
    }

    private void showGameMenu() {
        if (countDownTimer != null) countDownTimer.cancel();
        String[] menuItems = {"Tiếp tục", "Thoát game"};
        new AlertDialog.Builder(this)
                .setTitle("Tạm dừng")
                .setItems(menuItems, (dialog, which) -> {
                    if (which == 0) startTimer();
                    else finishAffinity();
                })
                .setCancelable(false)
                .show();
    }

    private void endGame() {
        if (countDownTimer != null) countDownTimer.cancel();
        final EditText input = new EditText(this);
        input.setHint("Nhập tên của bạn");
        new AlertDialog.Builder(this)
                .setTitle("Hoàn thành!")
                .setMessage("Bạn đạt " + score + " điểm.")
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("Lưu điểm", (d, w) -> {
                    String name = input.getText().toString().trim();
                    if (name.isEmpty()) name = "Người chơi";
                    HighScoreManager.saveScore(this, name, score);
                    finish();
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}