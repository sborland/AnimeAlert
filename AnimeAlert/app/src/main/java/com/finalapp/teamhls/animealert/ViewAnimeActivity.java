package com.finalapp.teamhls.animealert;

        import android.Manifest;
        import android.content.ContentResolver;
        import android.content.ContentValues;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.provider.CalendarContract;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.Spinner;
        import android.widget.Toast;


        import com.finalapp.teamhls.animealert.classes.AnimeChart;
        import com.finalapp.teamhls.animealert.classes.AnimeDB;
        import com.finalapp.teamhls.animealert.classes.UserChart;
        import com.finalapp.teamhls.animealert.classes.UserDB;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.LinkedHashMap;
        import java.util.Locale;
        import java.util.Map;
        import java.util.TimeZone;

public class ViewAnimeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String LOG_TAG = "My Log Tag";
    private int mal_num = 0;
    ArrayList<String> timeList;
    int[] offset = new int[]{0,5,10,20,30,45,60,120,180,240,300,360};
    public LinkedHashMap<String, String> times = new LinkedHashMap<String, String>();
    UserDB udb = new UserDB(this);
    AnimeDB adb = new AnimeDB(this);
    CheckBox notify_check_box;
    Spinner sp;
    ImageView LoadingImg;
    boolean added_anime = false;
    boolean remove_anime = false;
    int time_selection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anime);


        Log.i(LOG_TAG, "In ViewAnime");

        //grab the malNum from previous chart
        Bundle b = getIntent().getExtras();
        String malNum = b.getString("malNum");
        mal_num = Integer.parseInt(malNum);
        String url = "http://myanimelist.net/anime/" + malNum;

        notify_check_box = (CheckBox) findViewById(R.id.checkBox);
        LoadingImg = (ImageView) findViewById(R.id.loadingImg);

        Button retButton = (Button) findViewById(R.id.ReturnButton);
        retButton.setOnClickListener(this);

        //loads up webview
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                LoadingImg.setVisibility(view.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                LoadingImg.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);

        setTimes();
    }

    public void setTimes(){
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
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, timeList);
        sp.setAdapter(myAdapter);

        if (notify_check_box.isChecked()) {
            sp.setEnabled(false);
            sp.setClickable(false);
            sp.setSelection(6);
        }
        if(enableEventAdding()) {
            notify_check_box.setChecked(true);
            sp.setEnabled(false);
            sp.setClickable(false);
            sp.setSelection(6);
            remove_anime = true;
        }else {
            notify_check_box.setChecked(false);
        }
        // Setting listener for selection calls
        sp.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //if(!notify_check_box.isChecked())
        //    notify_check_box.setChecked(true);

        time_selection = offset[pos];
        Log.i(LOG_TAG, "offset: " + time_selection);

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void onCheck(View view) {
        if (notify_check_box.isChecked() && !added_anime) {
            sp.setEnabled(false);
            sp.setClickable(false);
            ContentResolver cr = getContentResolver();
            AnimeChart anime = adb.getAnimeByMalNum(mal_num);
            Log.i(LOG_TAG, "malnum" + mal_num);
            //Intent intent = new Intent(Intent.ACTION_INSERT);
            //intent.setData(CalendarContract.Events.CONTENT_URI);
            String eventUriStr = "content://com.android.calendar/events";
            //intent.setType("vnd.android.cursor.item/event");
            ContentValues event = new ContentValues();
            event.put("calendar_id", 1);
            event.put("title", anime.title);
            event.put("description", "Episode: " + anime.currEp);
            if(anime.simulCast.equals("false")) {
                anime.simulCast = "None";
            }
            event.put("eventLocation", anime.simulCast);
            event.put("eventTimezone", "UTC/GMT -8:00");
            Date date = new Date(anime.airDate * 1000);
            Log.i(LOG_TAG, "epoch time: " + anime.airDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC/GMT"));
            int day = Integer.parseInt(sdf.format(date));
            sdf = new SimpleDateFormat("MM", Locale.US);
            int month = Integer.parseInt(sdf.format(date));
            sdf = new SimpleDateFormat("yyyy", Locale.US);
            int year = Integer.parseInt(sdf.format(date));
            sdf = new SimpleDateFormat("HH", Locale.US);
            int hour = Integer.parseInt(sdf.format(date));
            sdf = new SimpleDateFormat("mm", Locale.US);
            int min = Integer.parseInt(sdf.format(date));

            Log.i(LOG_TAG, "date: " + month + "/" + day + "/"+ year +" - " + hour + ":" + min);
            // For next 1hr
            Calendar start = Calendar.getInstance();
            start.set(year, month-1, day, hour, min, 0);

            Calendar end = Calendar.getInstance();
            end.set(year, month-1, day, hour, min+30, 0);

            long startTime = start.getTimeInMillis();
            long endTime = end.getTimeInMillis();


            event.put("dtstart", startTime);
            event.put("dtend", endTime);
            //If it is bithday alarm or such kind (which should remind me for whole day) 0 for false, 1 for true
            // values.put("allDay", 1);
            event.put("eventStatus", 1);
            event.put("hasAlarm", 0);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Uri eventUri = cr.insert(CalendarContract.Events.CONTENT_URI, event);
            Log.i(LOG_TAG, eventUri.toString());

            long eventID = Long.parseLong(eventUri.getLastPathSegment());
            ContentValues reminderValues = new ContentValues();
            reminderValues.put("event_id", eventID);
            // Default value of the system. Minutes is a integer
            reminderValues.put("minutes", time_selection);
            // Alert Methods: Default(0), Alert(1), Email(2), SMS(3)
            reminderValues.put("method", 1);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues); //Uri reminderUri =

            //create a UserChart with eventID as userTime and update
            UserChart x = new UserChart();
            AnimeChart y = adb.getAnimeByMalNum(mal_num);
            x.setIsShort(y.isShort);
            x.setSimulCast(y.simulCast);
            x.setMalNum(y.malNum);
            x.setTitle(y.title);
            x.setAirDate(y.airDate);
            x.setCurrEp(y.currEp);
            x.setUserTime(eventID);
            udb.insert(x);
            added_anime = true;
            Log.i(LOG_TAG, "size of udb: " + udb.getUserChart().size());
            Toast.makeText(this, "added event & anime to udb!",
                    Toast.LENGTH_LONG).show();


        } else {
            //if it is checked
            sp.setEnabled(false);
            sp.setClickable(false);
            //we can remove anime
            if(remove_anime) {
                udb.delete(mal_num);
                Log.i(LOG_TAG, "deleted" + mal_num + "from udb");
                //delete reminder
            }
        }
    }



    private boolean enableEventAdding() {
        //Log.i(LOG_TAG, "finding " + mal_num);
        ArrayList<HashMap<String, String>> animeList = udb.getUserChart();
        //Log.i(LOG_TAG, "size of animeList: " + animeList.size());
        for (HashMap<String,String> x : animeList){
           for (Map.Entry entry : x.entrySet()){
               String key = entry.getKey() + "";
               String val = entry.getValue() + "";
               //Log.i(LOG_TAG,"key: " + key + "val: " + val);
               if(key.equals("malNum") && Integer.parseInt(val) == mal_num ) {
                   //Log.i(LOG_TAG, "malnum is: " + entry.getValue() + "mal_num is: " + mal_num);
                   added_anime = true;
                   return true;
               }
           }
        }
        return false;
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