package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.finalapp.teamhls.animealert.classes.AnimeChart;
import com.finalapp.teamhls.animealert.classes.AnimeDB;
import com.finalapp.teamhls.animealert.classes.UserChart;
import com.finalapp.teamhls.animealert.classes.UserDB;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentChartActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_chart);
        Log.i(LOG_TAG, "In CurrentCharts");

        Button optButton = (Button) findViewById(R.id.OptButton);
        optButton.setOnClickListener(this);
        Button testyButton = (Button) findViewById(R.id.TestButton);
        testyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String vStr = (String) view.getTag();
        Log.i(LOG_TAG, vStr);
        if (vStr.equalsIgnoreCase("return")) {
            finish();
        }
        //Test button that showes the Norn9 MAL site in the ViewAnimeActivity
        //the malNum is the URL code for Norn9
        if (vStr.equalsIgnoreCase("testanime")) {
            Intent testAnime = new Intent(CurrentChartActivity.this, ViewAnimeActivity.class);
            UserDB udb = new UserDB(this);
            AnimeDB adb = new AnimeDB(this);
            UserChart x = new UserChart();
            AnimeChart y = adb.getAnimeByMalNum(31414);
            x.setUserTime(y.airDate);
            x.setCurrEp(y.currEp);
            x.setIsShort(y.isShort);
            x.setSimulCast(y.simulCast);
            x.setMalNum(y.malNum);
            x.setAirDate(y.airDate);
            x.setTitle(y.title);
            Log.i(LOG_TAG, "Added title: " + y.title);
            udb.insert(x);


            ArrayList<HashMap<String, String>> userList = udb.getUserChart();
            for (HashMap<String,String> a : userList){
                for (Map.Entry entry : a.entrySet()){
                    Log.i(LOG_TAG,entry.getKey() +" "+ entry.getValue());
                }
            }

            Log.i(LOG_TAG, "userList size: " + userList.size());
            //UserChart z = udb.getAnimeByMalNum(31414);
            //Log.i(LOG_TAG, "z: " + z.title);
            testAnime.putExtra("malNum","31414");
            CurrentChartActivity.this.startActivity(testAnime);
        }
    }
}
