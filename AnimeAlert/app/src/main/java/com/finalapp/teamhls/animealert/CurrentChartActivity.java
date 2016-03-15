package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.finalapp.teamhls.animealert.classes.AnimeDB;
import com.finalapp.teamhls.animealert.classes.AnimeListView;

import com.finalapp.teamhls.animealert.classes.DownloadCover;

import java.util.ArrayList;
import java.util.HashMap;

import com.finalapp.teamhls.animealert.classes.AnimeChart;
import com.finalapp.teamhls.animealert.classes.UserChart;
import com.finalapp.teamhls.animealert.classes.UserDB;

/*
Class: CurrentChartActivity
Summary: Displays a list of current anime (found in currentChart.db)
         with their information to the user. When the user
         selects an anime, the app takes them to ViewAnimeActivity
         with the MAL ID of the selected anime.
*/

public class CurrentChartActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";
    AnimeListView adapter;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_chart);
        Log.i(LOG_TAG, "In CurrentCharts");
        //initialize variables
        list = (ListView) findViewById(R.id.CurrentChartList);
        Button optButton = (Button) findViewById(R.id.OptButton);
        optButton.setOnClickListener(this);
        createListView();

    }


public void createListView(){
    AnimeDB currentDB = new AnimeDB(this);
    ArrayList<HashMap<String, String>> animelist = currentDB.getAnimeChart();
    int size = animelist.size();
    String[] imgurlArr = new String[size];
    String[] summaryArr = new String[size];
    String[] titleArr = new String[size];
    String[] malIDArr = new String[size];
    int n = 0;
    //grabs and organizes each show's data to be sent to the AnimeListView
    for (HashMap<String,String> x : animelist) {
        String s = x.get("malNum");
          //  Log.i(LOG_TAG, "title " + s);
            String[] animeData = {x.get("img"),x.get("sum")};
            //if SplashActivity wasn't able to grab url for the image,
            //the app tries to grab it again
            if (x.get("img")==null) {
               // Log.i(LOG_TAG, "Grabbing data for "+s);
                animeData = DownloadCover.getCover(Integer.parseInt(s));
            }
            String summary = animeData[1];
            String imgurl = animeData[0];
            String title = currentDB.getAnimeByMalNum(Integer.parseInt(s)).title;
           // Log.i(LOG_TAG, "title " + title);
            imgurlArr[n] = imgurl;
            summaryArr[n] = summary;
            titleArr[n] = title;
            malIDArr[n]=s;
            n++;
        
    }
    adapter = new AnimeListView(CurrentChartActivity.this,titleArr,summaryArr,imgurlArr,malIDArr );
    list.setAdapter(adapter);

    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent viewAnime = new Intent(CurrentChartActivity.this, ViewAnimeActivity.class);
            Log.i(LOG_TAG, "ID: " + view.getId());
            String ID = Integer.toString(view.getId());
            viewAnime.putExtra("malNum", ID);
            CurrentChartActivity.this.startActivity(viewAnime);
        }
    });
    Log.i(LOG_TAG, "Size of list: "+ size);

}

    @Override
    protected void onResume() {
        super.onResume();
        createListView();
    }


    @Override
    public void onClick(View view) {
        String vStr = (String) view.getTag();
        Log.i(LOG_TAG, vStr);
        if (vStr.equalsIgnoreCase("return")) {
            finish();
        }

    }
}
