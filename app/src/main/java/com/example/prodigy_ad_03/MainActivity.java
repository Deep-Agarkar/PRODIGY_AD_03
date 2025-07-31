package com.example.prodigy_ad_03;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prodigy_ad_03.R;

public class MainActivity extends AppCompatActivity {

    private TextView stopwatchDisplay;
    private Button startButton, pauseButton, resetButton;

    private long startTime = 0L;
    private long timeBuffer = 0L;
    private long updateTime = 0L;
    private Handler handler;
    private boolean isRunning = false;

    private final Runnable updateTimer = new Runnable() {
        public void run() {
            long currentTime = System.currentTimeMillis();
            updateTime = timeBuffer + (currentTime - startTime);

            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int millis = (int) (updateTime % 1000);

            String time = String.format("%02d:%02d:%03d", mins, secs, millis);
            stopwatchDisplay.setText(time);
            handler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchDisplay = findViewById(R.id.stopwatchDisplay);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();

        startButton.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                handler.post(updateTimer);
                isRunning = true;
            }
        });

        pauseButton.setOnClickListener(v -> {
            if (isRunning) {
                timeBuffer += System.currentTimeMillis() - startTime;
                handler.removeCallbacks(updateTimer);
                isRunning = false;
            }
        });

        resetButton.setOnClickListener(v -> {
            startTime = 0L;
            timeBuffer = 0L;
            updateTime = 0L;
            isRunning = false;
            handler.removeCallbacks(updateTimer);
            stopwatchDisplay.setText("00:00:000");
        });
    }
}
