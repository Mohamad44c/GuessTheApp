package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class HardLevel extends AppCompatActivity {
    public int counter = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_level);

        final TextView counttime = findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                counttime.setText("Game Over");
                startActivity(new Intent(HardLevel.this, MainActivity.class));
            }
        }.start();
    }
}