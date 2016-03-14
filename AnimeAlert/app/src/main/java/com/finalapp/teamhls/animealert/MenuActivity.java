package com.finalapp.teamhls.animealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button userButton = (Button) findViewById(R.id.UserChartButton);
        userButton.setOnClickListener(this);
        Button currentButton = (Button) findViewById(R.id.CurrentChartButton);
        currentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String vStr = (String) view.getTag();
        Log.i(LOG_TAG, vStr);
        if (vStr.equalsIgnoreCase("userchart")) {
            Intent UserChart = new Intent(MenuActivity.this, UserChartActivity.class);
            MenuActivity.this.startActivity(UserChart);
        }
         if (vStr.equalsIgnoreCase("currentchart")) {
                Intent CurrentChart= new Intent(MenuActivity.this, CurrentChartActivity.class);
                MenuActivity.this.startActivity(CurrentChart);
         }

    }
}
