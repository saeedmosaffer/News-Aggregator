package edu.birzeit.saeedmosaffer.newsaggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTimer = new CountDownTimer(2000, 1000) { // 2000 milliseconds (2 seconds)
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, GuidanceActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        };

        countDownTimer.start();
        }
}