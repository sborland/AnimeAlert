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

public class CurrentChartActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";
    AnimeListView adapter;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_chart);
        Log.i(LOG_TAG, "In CurrentCharts");
        list = (ListView) findViewById(R.id.CurrentChartList);
        Button optButton = (Button) findViewById(R.id.OptButton);
        optButton.setOnClickListener(this);
        Button testyButton = (Button) findViewById(R.id.TestButton);
        testyButton.setOnClickListener(this);
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
    for (HashMap<String,String> x : animelist) {
        String s = x.get("malNum");


            Log.i(LOG_TAG, "title " + s);

            //for (Map.Entry entry : x.entrySet()) {
            //   Log.i(LOG_TAG,entry.getKey() +" "+ entry.getValue());
            String[] animeData = {x.get("img"),x.get("sum")};
            if (x.get("img")==null) {
                Log.i(LOG_TAG, "Grabbing data for "+s);
                animeData = DownloadCover.getCover(Integer.parseInt(s));
            }
            String summary = animeData[1];
            String imgurl = animeData[0];
            String title = currentDB.getAnimeByMalNum(Integer.parseInt(s)).title;
            Log.i(LOG_TAG, "title " + title);
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
    Log.i(LOG_TAG, "SIZE THIS: "+ size);


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
        //Test button that showes the Norn9 MAL site in the ViewAnimeActivity
        //the malNum is the URL code for Norn9

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


           // Log.i(LOG_TAG, "userList size: " + userList.size());
            //UserChart z = udb.getAnimeByMalNum(31414);
            //Log.i(LOG_TAG, "z: " + z.title);
            testAnime.putExtra("malNum","31414");
            CurrentChartActivity.this.startActivity(testAnime);
        }
    }
}
