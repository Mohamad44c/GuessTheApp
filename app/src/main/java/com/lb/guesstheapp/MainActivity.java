package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements Serializable {
    String result;

    List<String> URLMatches = new ArrayList<String>();
    List<String> NameMatches = new ArrayList<String>();
    String imgUrls[];
    String appNames[];

    List<String> urlsActual = new ArrayList<String>();
    List<String> namesActual = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button easy = (Button) findViewById(R.id.easyLevel);
        Button medium = (Button) findViewById(R.id.mediumLevel);
        Button hard = (Button) findViewById(R.id.hardLevel);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent easyIntent = new Intent(MainActivity.this, EasyLevel.class);
//                Passing list of data (urls and app names)
                easyIntent.putExtra("urlsActual", (Serializable) urlsActual);
                easyIntent.putExtra("namesActual", (Serializable) namesActual);
                startActivity(easyIntent);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mediumIntent = new Intent(MainActivity.this, MediumLevel.class);
                mediumIntent.putExtra("urlsActual", (Serializable) urlsActual);
                mediumIntent.putExtra("namesActual", (Serializable) namesActual);
                startActivity(mediumIntent);
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hardIntent = new Intent(MainActivity.this, HardLevel.class);
                hardIntent.putExtra("urlsActual", (Serializable) urlsActual);
                hardIntent.putExtra("namesActual", (Serializable) namesActual);
                startActivity(hardIntent);
            }
        });


        DownloadTask task = new DownloadTask();
        //downloading source code of this page
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

//                    extract urls and app names
                    Matcher match = Pattern.compile("data-image-loader=\"(.*?)\"").matcher(result);
                    Matcher match2 = Pattern.compile("alt=\"(.*?)\"").matcher(result);


                    while (match.find()) {
                        URLMatches.add(match.group());
                    }

                    while (match2.find()) {
                        NameMatches.add(match2.group());
                    }
                }
                imgUrls = URLMatches.toArray(new String[0]);
                appNames = NameMatches.toArray(new String[0]);

//                removing data-image-loader="" and alt=""
                for (int i = 0; i < imgUrls.length; i++) {
                    urlsActual.add(imgUrls[i].substring(19, imgUrls[i].length() - 1));
                }
                for (int i = 5; i < appNames.length; i++) {
                    namesActual.add(appNames[i].substring(5, appNames[i].length() - 7));
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }
}