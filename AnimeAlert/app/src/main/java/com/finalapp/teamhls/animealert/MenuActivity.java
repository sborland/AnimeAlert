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

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    public static String LOG_TAG = "My Log Tag";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDER= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        CheckPermissions();

        Button userButton = (Button) findViewById(R.id.UserChartButton);
        userButton.setOnClickListener(this);
        Button currentButton = (Button) findViewById(R.id.CurrentChartButton);
        currentButton.setOnClickListener(this);
    }

   public void CheckPermissions() {
       //Check for calender permission
       if (ContextCompat.checkSelfPermission(MenuActivity.this,
               Manifest.permission.WRITE_CALENDAR)
               != PackageManager.PERMISSION_GRANTED) {
           // Should we show an explanation?
           if (ActivityCompat.shouldShowRequestPermissionRationale(MenuActivity.this,
                   Manifest.permission.WRITE_CALENDAR)) {
               // Show an expanation to the user *asynchronously* -- don't block
               // this thread waiting for the user's response! After the user
               // sees the explanation, try again to request the permission.
           } else {
               // No explanation needed, we can request the permission.
               ActivityCompat.requestPermissions(MenuActivity.this,
                       new String[]{Manifest.permission.WRITE_CALENDAR},
                       MY_PERMISSIONS_REQUEST_WRITE_CALENDER);
           }
       }
   }

       @Override
       //Check permission response
       public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
           switch (requestCode) {
               case MY_PERMISSIONS_REQUEST_WRITE_CALENDER: {
                   // If request is cancelled, the result arrays are empty.
                   if (grantResults.length > 0
                           && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       // permission was granted, yay! Do the
                       // contacts-related task you need to do.
                   } else {
                      String  status = "Please grant permission for app to read and write to Calender";
                       Log.i(LOG_TAG, "Status: " + status);

                       /////////////////////////////DO THIS AT SOME POINT
                       // permission denied, boo! Disable the
                       // functionality that depends on this permission.
                   }
                   return;
               }
               // other 'case' lines to check for other
               // permissions this app might request
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
