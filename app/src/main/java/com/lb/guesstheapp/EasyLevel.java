package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OptionalDataException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class EasyLevel extends AppCompatActivity {
    ImageView imageView;
    Button b1, b2, b3, b4;
    int x;
    ArrayList<String> urlList;
    ArrayList<String> nameList;
    Bitmap icon;
    ImageDownloader task;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        ArrayList<String> urlList;
        ArrayList<String> nameList;

        imageView = (ImageView) findViewById(R.id.imageView);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        Bundle bundle = getIntent().getExtras();
        urlList = (ArrayList<String>) bundle.getStringArrayList("urlsActual");
        nameList = (ArrayList<String>) bundle.getSerializable("namesActual");

        for (int i = 0; i < urlList.size(); i++) {
            System.out.println(urlList.get(i));
        }
        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(nameList.get(i));
        }
        random = new Random();
        x = random.nextInt(70) + 1;

//        DOWNLOADING APP IMAGE AND DISPLAYING
        try {
            task = new ImageDownloader();
            icon = task.execute(urlList.get(x)).get();
            b1.setText(nameList.get(x));
            imageView.setImageBitmap(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

//      RANDOM APP NAMES FOR THE BUTTONS
        b2.setText(nameList.get(random.nextInt(70) + 1));
        b3.setText(nameList.get(random.nextInt(70) + 1));
        b4.setText(nameList.get(random.nextInt(70) + 1));
    }

    //  CHECKING ANSWER
    public void checkAnswer(View view) {
        if (view.getId() == R.id.button1) {
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
        }
        try {
//            GET EXTRAS FROM MAIN
            Bundle bundle = getIntent().getExtras();
            urlList = (ArrayList<String>) bundle.getStringArrayList("urlsActual");
            nameList = (ArrayList<String>) bundle.getSerializable("namesActual");
//            DOWNLOAD NEW IMAGE
            task = new ImageDownloader();
            random = new Random();
            x = random.nextInt(70) + 1;
            icon = task.execute(urlList.get(x)).get();
//            SET BUTTON TEXTS
            b1.setText(nameList.get(x));
            b2.setText(nameList.get(random.nextInt(70) + 1));
            b3.setText(nameList.get(random.nextInt(70) + 1));
            b4.setText(nameList.get(random.nextInt(70) + 1));
//            DISPLAY NEW IMAGE
            imageView.setImageBitmap(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        //  FUNCTION TO DOWNLOAD IMAGE IN THE BACKGROUND
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
