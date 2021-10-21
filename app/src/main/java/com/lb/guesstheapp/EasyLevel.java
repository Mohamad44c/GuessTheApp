package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        imageView = (ImageView) findViewById(R.id.imageView);

        DownloadTask task = new DownloadTask();

        try {
            result = task.execute("https://www.pcmag.com/picks/best-android-apps").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpsURLConnection urlConnection;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();

                bufferedReader.readLine();

                while ((result = bufferedReader.readLine()) != null) {
                    builder.append(result);
//                    System.out.println(result);//html src code

                    Pattern p = Pattern.compile("data-image-loader=\"(.*?)\"");
                    Pattern alt = Pattern.compile("alt=\"(.*?)\"");
//                    regex codes to get src and alt
                    Matcher m = p.matcher(result);
                    Matcher m2 = alt.matcher(result);


                    while (m.find()) {
                        System.out.println(m.group(1));
//                        String imgs = m.group(1);
//
//                        String urls[] = imgs.split(".png");
//
//                        for (int i = 0; i < urls.length; i++) {
//                            System.out.println("URL " + i + " " + urls[i]);
//                        }
                    }
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    // the shit code below could be used to download the icons we need to display
    public void DownloadImage(View view) {
        ImageDownloader task2 = new ImageDownloader();
        AsyncTask<String, Void, Bitmap> icon;
        try {
            icon = task2.execute("");
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
