package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
            testAnime.putExtra("malNum","31452");
            CurrentChartActivity.this.startActivity(testAnime);
        }
    }
}
