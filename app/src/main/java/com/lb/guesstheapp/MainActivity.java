package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String result;
    String group = "";
    List<String> URLMatches = new ArrayList<String>();
    List<String> NameMatches = new ArrayList<String>();
    String imgUrls[];
    String appNames[];

    String urlsActual[];
    String namesActual[];

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
                easyIntent.putExtra("result", result);
                startActivity(easyIntent);

            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mediumIntent = new Intent(MainActivity.this, MediumLevel.class);
//                mediumIntent.putExtra();
                startActivity(mediumIntent);
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hardIntent = new Intent(MainActivity.this, HardLevel.class);
//                hardIntent.putExtra();
                startActivity(hardIntent);
            }
        });

//        downloading source code from website

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


// *********************** *********************** *********************** *********************** ***********************

//                String urls = imgUrls[0].substring(19, imgUrls[0].length() - 1);
//                String names = appNames[5].substring(5, appNames[5].length() - 7);
                urlsActual = new String[1000];
                namesActual = new String[1000];
                for (int i = 0; i < imgUrls.length; i++) {
                    urlsActual[i] = imgUrls[i].substring(19, imgUrls[i].length() - 1);
//                    System.out.println(urlsActual[i]);
                }
                for (int i = 5; i < appNames.length; i++) {
                    namesActual[i] = appNames[i].substring(5, appNames[i].length() - 7);
//                    System.out.println(namesActual[i]);
                }


// *********************** *********************** *********************** *********************** ***********************
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }


//    fetch apps (names and icons) from this link https://www.pcmag.com/picks/best-android-apps

//    1. Display the apps; we have to guess their names based on the icon

//    2. apply regular expressions after fetching the content of the webpage to extract the URL
//       of the images and the correct answer

//    3. Display the icon and 4 buttons underneath, 1 one the buttons should contain the right answer

//    4. Easy level: display icon with 4 possible answers

//    5. Medium: Same as easy level but with scores: Correct +2; Wrong: -1;

//    6. Hard Level: All of the above but with a timer e.g 30 seconds to get the highest score

//    Whole app should be 4 pages:

//    Home page: Allows user to choose a level

//    + 3 different pages for different levels

//    We have to take the image urls from the website and use the .split and .substring to get the src(image url),
//    alt(image name) but the alt has "Image" for each name, have to remove it
//    to use regex => we use Pattern p = Pattern.compile("regex in here");
//    example: String txt = "Mississippi";
//    Pattern p = Pattern.compile("Mi(.*?)pi"); means to print everything between Mi and pi
//    We can use Pattern p = Pattern.compile("<img src=\"(.*?)\""); return everything between the "" in the src tag
//    we have to create another object called Matcher match = p.matcher(txt);
//    while(m.find()){
//    sysout(m.group(1));}
//    for the alt Pattern p = Pattern.compile("<img alt=\"(.*?)\"");

}