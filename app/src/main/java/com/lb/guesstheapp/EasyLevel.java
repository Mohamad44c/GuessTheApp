package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class EasyLevel extends AppCompatActivity {
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        imageView = (ImageView) findViewById(R.id.imageView);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://www.pcmag.com/picks/best-android-apps").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                Pattern p;
                Matcher match = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                bufferedReader.readLine();

                while ((result = bufferedReader.readLine()) != null) {
                    builder.append(result);
                    Log.i("Result: ", result);//html src code
                    p = Pattern.compile("<img src=\"(.*?)\"");
                    match = p.matcher(result);
                }
                while (match.find()) {
                    System.out.println(match.group(1));
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }
}
