package com.finalapp.teamhls.animealert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.finalapp.teamhls.animealert.classes.WebViewController;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ViewAnimeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String LOG_TAG = "My Log Tag";

    ArrayList<String> timeList;
    public LinkedHashMap<String, String> times = new LinkedHashMap<String, String>();

    CheckBox notify_check_box;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anime);
        Log.i(LOG_TAG, "In ViewAnime");

        //grab the malNum from previous chart
        Bundle b = getIntent().getExtras();
        String malNum = b.getString("malNum");
        String url = "http://myanimelist.net/anime/" + malNum;

        notify_check_box = (CheckBox) findViewById(R.id.checkBox);

        Button retButton = (Button) findViewById(R.id.ReturnButton);
        retButton.setOnClickListener(this);

        //loads up webview
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.loadUrl(url);

        setTimes();
    }

    public void setTimes(){
        times.put("Not at all", "-1");
        times.put("On airing", "0");
        times.put("5 Minutes before", "5");
        times.put("10 Minutes before", "10");
        times.put("20 Minutes before", "20");
        times.put("30 Minutes before", "30");
        times.put("45 Minutes before", "45");
        times.put("1 Hour before", "60");
        times.put("2 Hours before", "120");
        times.put("3 Hours before", "180");
        times.put("4 Hours before", "240");
        times.put("5 Hours before", "300");
        times.put("6 Hours before", "360");
    }


    @Override
    protected void onStart(){
        super.onStart();
        sp = (Spinner) findViewById(R.id.spinner);

        // Extract color list as keySet from colors hashmap
        timeList =  new ArrayList<String>(times.keySet());
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, timeList);
        sp.setAdapter(myAdapter);

        if (notify_check_box.isChecked()){

        } else {
            sp.setEnabled(false);
            sp.setClickable(false);
            sp.setSelection(6);
        }

        // Setting listener for selection calls
        sp.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //if(!notify_check_box.isChecked())
        //    notify_check_box.setChecked(true);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onCheck(View view){
        if(notify_check_box.isChecked()){
            sp.setEnabled(true);
            sp.setClickable(true);
        } else {
            sp.setEnabled(false);
            sp.setClickable(false);
        }
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