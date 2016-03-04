package com.finalapp.teamhls.animealert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserChartActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chart);
        Log.i(LOG_TAG, "In UserCharts");

        Button optButton = (Button) findViewById(R.id.OptButton);
        optButton.setOnClickListener(this);
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
