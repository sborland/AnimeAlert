package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.finalapp.teamhls.animealert.classes.AnimeListView;
import com.finalapp.teamhls.animealert.classes.DownloadCover;
import com.finalapp.teamhls.animealert.classes.UserDB;

import java.util.ArrayList;
import java.util.HashMap;

/*Class: CurrentChartActivity
  Summary: Displays a list of the user's selected anime
          (found in userChart.db) with their information
           to the user. When the user selects an anime,
           the app takes them to ViewAnimeActivity
           with the MAL ID of the selected anime.
        */
public class UserChartActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";
    ListView chartList;
    AnimeListView adapter;
    TextView checkthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chart);
        chartList = (ListView) findViewById(R.id.UserChartList);
        checkthis = (TextView) findViewById(R.id.txtCheck);
        Log.i(LOG_TAG, "In UserCharts");
        createListView();

    }
    @Override
    protected void onResume() {
        super.onResume();
        createListView();
    }

    //populates the list view with user's anime
    public void createListView(){
        UserDB userDB = new UserDB(this);
        ArrayList<HashMap<String, String>> animelist = userDB.getUserChart();
        int size = animelist.size();
        if (size==0){
            checkthis.setVisibility(View.VISIBLE);
        }else{
            checkthis.setVisibility(View.GONE);
        }
        String[] imgurlArr = new String[size];
        String[] summaryArr = new String[size];
        String[] titleArr = new String[size];
        String[] malIDArr = new String[size];
        int n = 0;
        for (HashMap<String,String> x : animelist) {
            String s = x.get("malNum");
            String[] animeData = {x.get("img"),x.get("sum")};
            if (x.get("img")==null) {
               // Log.i(LOG_TAG, "Grabbing data for "+s);
                animeData = DownloadCover.getCover(Integer.parseInt(s));
            }
            String summary = animeData[1];
            String imgurl = animeData[0];
            String title = userDB.getAnimeByMalNum(Integer.parseInt(s)).title;
           // Log.i(LOG_TAG, "title " + title);
            imgurlArr[n] = imgurl;
            summaryArr[n] = summary;
            titleArr[n] = title;
            malIDArr[n]=s;
            n++;

        }
        adapter = new AnimeListView(UserChartActivity.this,titleArr,summaryArr,imgurlArr,malIDArr );
        chartList.setAdapter(adapter);
        chartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewAnime = new Intent(UserChartActivity.this, ViewAnimeActivity.class);
                Log.i(LOG_TAG, "ID: " + view.getId());
                String ID = Integer.toString(view.getId());
                viewAnime.putExtra("malNum", ID);
                UserChartActivity.this.startActivity(viewAnime);
            }
        });
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
