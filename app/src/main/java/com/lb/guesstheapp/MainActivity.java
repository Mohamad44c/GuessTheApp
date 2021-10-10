package com.lb.guesstheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
                startActivity(new Intent(MainActivity.this, EasyLevel.class));
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MediumLevel.class));
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HardLevel.class));
            }
        });
    }


//    fetch apps (names and icons) from this link https://www.pcmag.com/picks/best-android-apps

//    1. Display the apps; we have to guess their names based on the icon

//    2. apply regular expressions after fetching the content of the webpage to extract the URL of the images and the correct answer

//    3. Display the icon and 4 buttons underneath, 1 one the buttons should contain the right answer

//    4. Easy level: display icon with 4 possible answers

//    5. Medium: Same as easy level but with scores: Correct +2; Wrong: -1;

//    6. Hard Level: All of the above but with a timer e.g 30 seconds to get the highest score

//    Whole app should be 4 pages:

//    Home page: Allows user to choose a level

//    + 3 differnt pages for different levels

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