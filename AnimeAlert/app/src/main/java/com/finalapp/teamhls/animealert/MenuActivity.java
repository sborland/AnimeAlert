package com.finalapp.teamhls.animealert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.finalapp.teamhls.animealert.classes.AnimeDB;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/*Class: MenuActivity
  Summary: Display user options of either seeing
           their list of anime alerts or the list
           of current anime season. Selecting either
           of them will take the user to the appropriate
           UserChartActivity or CurrentChartActivity.
           Also asks permission for calender access.
*/
public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR=1;
    File currentDB;
    AnimeDB currentChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button userButton = (Button) findViewById(R.id.UserChartButton);
        userButton.setOnClickListener(this);
        Button currentButton = (Button) findViewById(R.id.CurrentChartButton);
        currentButton.setOnClickListener(this);
        currentDB = getApplicationContext().getDatabasePath("currentChart.db");
        currentChart = new AnimeDB(this);
        ArrayList<HashMap<String, String>> animelist = currentChart.getAnimeChart();
        //checks if we actually got the database or not
        //Log.i(LOG_TAG, "Size" + animelist.size());
        if (animelist.size()==0){
            this.deleteDatabase("currentChart.db");
            restartThis();
        }
        requestPermission();
    }

    private void restartApp() {
        Intent intent = new Intent(MenuActivity.this, SplashActivity.class);
        startActivity(intent);
    }
    public void restartThis(){
        restartApp();
        finish();
    }

   private void requestPermission() {

       if (ContextCompat.checkSelfPermission(this,
               Manifest.permission.WRITE_CALENDAR)
               != PackageManager.PERMISSION_GRANTED) {

           if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                   Manifest.permission.WRITE_CALENDAR)) {

           } else {
               ActivityCompat.requestPermissions(this,
                       new String[]{Manifest.permission.WRITE_CALENDAR},
                       MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
           }
       }
   }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Please allow the app to access your calender.",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
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
