package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class EasyLevel extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void DownloadIcon(View view) {
        IconDownloader task = new IconDownloader();
        Bitmap downloadedImg;
        try {
            downloadedImg = task.execute("https://animalfactguide.com/wp-content/uploads/2014/04/koala2.jpg").get();
            imageView.setImageBitmap(downloadedImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class IconDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();//gets data
                Bitmap downloadedImage = BitmapFactory.decodeStream(in);
                return downloadedImage;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}