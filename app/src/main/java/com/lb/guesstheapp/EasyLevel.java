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
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        imageView = (ImageView) findViewById(R.id.imageView);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> urlList = (ArrayList<String>) bundle.getStringArrayList("urlsActual");
        ArrayList<String> nameList = (ArrayList<String>) bundle.getSerializable("namesActual");

        for (int i = 0; i < urlList.size(); i++) {
            System.out.println(urlList.get(i));
        }
        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(nameList.get(i));
        }
        Random random = new Random();
        int x = random.nextInt(50) + 1;

//        DOWNLOADING IMAGE AND DISPLAYING
        ImageDownloader task = new ImageDownloader();
        Bitmap icon;
        try {
            icon = task.execute(urlList.get(x)).get();
            imageView.setImageBitmap(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
