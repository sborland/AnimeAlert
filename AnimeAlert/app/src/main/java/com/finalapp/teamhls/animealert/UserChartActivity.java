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

import java.util.ArrayList;

public class UserChartActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chart);
        Log.i(LOG_TAG, "In UserCharts");

        ListView chartList = (ListView) findViewById(R.id.CurrentChartList);


        //Temporary
        String[] anime = new String [] {"404", "Cowboy Bebop", "404", "404", "404", "Cowboy Bebop: Tengoku no Tobira","Trigun",
                "Witch Hunter Robin","Beet the Vandel Buster","404", "404", "404", "404",
                "404","404","Eyeshield 21","Hachimitsu to Clover","Hungry Heart: Wild Striker"};
        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < anime.length; ++i) {
            list.add(anime[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        chartList.setAdapter(adapter);

        chartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                String mal_id = Long.toString(id);
                Intent testAnime = new Intent(UserChartActivity.this, ViewAnimeActivity.class);
                testAnime.putExtra("malNum",mal_id);
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
