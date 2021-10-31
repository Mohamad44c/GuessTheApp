package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class HardLevel extends AppCompatActivity {
    public int counter = 30;
    ImageView imageView;
    Button b1, b2, b3, b4;
    TextView scoreText;
    int score = 0;
    ArrayList<String> urlList;
    ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_level);

        imageView = (ImageView) findViewById(R.id.imageView);
        scoreText = (TextView) findViewById(R.id.score);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        Bundle bundle = getIntent().getExtras();
        urlList = (ArrayList<String>) bundle.getStringArrayList("urlsActual");
        nameList = (ArrayList<String>) bundle.getSerializable("namesActual");

        Random random = new Random();
        int x = random.nextInt(50) + 1;

//        DOWNLOADING IMAGE AND DISPLAYING
        ImageDownloader task = new ImageDownloader();
        Bitmap icon;
        try {
            icon = task.execute(urlList.get(x)).get();
            b1.setText(nameList.get(x));
            imageView.setImageBitmap(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        BUTTON TEXTS
        b2.setText(nameList.get(random.nextInt(70) + 1));
        b3.setText(nameList.get(random.nextInt(70) + 1));
        b4.setText(nameList.get(random.nextInt(70) + 1));

//        COUNTDOWN TIMER
        final TextView countTime = findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countTime.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                countTime.setText("Game Over");
                startActivity(new Intent(HardLevel.this, MainActivity.class));
            }
        }.start();
    }

    public void checkAnswer(View view) {
        if (view.getId() == R.id.button1) {
            score = score + 2;
        } else {
            score = score - 1;
        }
        scoreText.setText("Score: " + score);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                InputStream in = connection.getInputStream();
                Bitmap icon = BitmapFactory.decodeStream(in);

                return icon;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}