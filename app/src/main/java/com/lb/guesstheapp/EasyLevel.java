package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OptionalDataException;
import java.net.HttpURLConnection;
import java.net.URL;
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

        ImageDownloader task2 = new ImageDownloader();
//        Bitmap icon;
//        try {
//            icon = task2.execute("https://i.pcmag.com/imagery/collection-group-product/03x8j28CrTYoWktBySjR3PX.1607112323.fit_lim.size_723x.png").get();
//            imageView.setImageBitmap(icon);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        DownloadTask task = new DownloadTask();
        String link = "https://www.pcmag.com/picks/best-android-apps";
        try {
            result = task.execute(link).get();
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

                    Pattern dataimg = Pattern.compile("data-image-loader=\"(.*?)\"");
                    Pattern alt = Pattern.compile("alt=\"(.*?)\"");

                    Matcher m1 = dataimg.matcher(result);
                    Matcher m2 = alt.matcher(result);

                    while (m1.find()) {
                        System.out.println(m1.group(1));
//                        String group = m1.group(1);
//                        String arr[] = group.split(".png");
//
//                        for (int i = 0; i < arr.length; i++) {
//                            System.out.println("Array element  " + i + " " + arr[i] + ".png");
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

    //  used to download the icons we need to display
    public void DownloadImage() {
        ImageDownloader task2 = new ImageDownloader();
        Bitmap icon;
        try {
            icon = task2.execute("https://i.pcmag.com/imagery/collection-group-product/03x8j28CrTYoWktBySjR3PX.1607112323.fit_lim.size_723x.png").get();
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
