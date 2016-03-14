package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.finalapp.teamhls.animealert.classes.UserChart;
import com.finalapp.teamhls.animealert.classes.UserDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserChartActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";
    UserDB udb = new UserDB(this);
    ListView chartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chart);
        Log.i(LOG_TAG, "In UserCharts");
        createList();
    }
    @Override
    protected void onResume() {
        super.onResume();
        createList();
    }

    public void createList(){
        chartList = (ListView) findViewById(R.id.CurrentChartList);


        //Temporary
        //String[] anime = new String [] {"404", "Cowboy Bebop", "404", "404", "404", "Cowboy Bebop: Tengoku no Tobira","Trigun",
        //        "Witch Hunter Robin","Beet the Vandel Buster","404", "404", "404", "404",
        //        "404","404","Eyeshield 21","Hachimitsu to Clover","Hungry Heart: Wild Striker"};

        final ArrayList<String> malNum_list = new ArrayList<String>();
        ArrayList<HashMap<String, String>> animelist = udb.getUserChart();
        for (HashMap<String,String> x : animelist){
            for (Map.Entry entry : x.entrySet()){
                if(entry.getKey().toString().equals("malNum")) {
                    Log.i(LOG_TAG, "malNum: " + entry.getKey());
                    malNum_list.add(entry.getValue().toString());
                }
            }
        }

        ArrayList<String> title_list = new ArrayList<String>();
        for(String malNum: malNum_list) {
            UserChart anime = udb.getAnimeByMalNum(Integer.parseInt(malNum));
            title_list.add(anime.title);
        }
        /*for (int i = 0; i < anime.length; ++i) {
            list.add(anime[i]);
        }*/
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, title_list);
        chartList.setAdapter(adapter);

        chartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //String mal_id = Long.toString(id);
                Intent testAnime = new Intent(UserChartActivity.this, ViewAnimeActivity.class);
                Log.i(LOG_TAG, "malnum at position: " + malNum_list.get(position));
                testAnime.putExtra("malNum", malNum_list.get(position));
                UserChartActivity.this.startActivity(testAnime);
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
