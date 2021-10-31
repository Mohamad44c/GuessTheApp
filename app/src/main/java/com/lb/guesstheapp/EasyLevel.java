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
        String result = bundle.getString("result");
        System.out.println("FROM MAIN" + result);

        //        EasyLevel.ImageDownloader task2 = new EasyLevel.ImageDownloader();
//        Bitmap icon;
//        try {
//            icon = task2.execute("https://i.pcmag.com/imagery/collection-group-product/03x8j28CrTYoWktBySjR3PX.1607112323.fit_lim.size_723x.png").get();
//            imageView.setImageBitmap(icon);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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
