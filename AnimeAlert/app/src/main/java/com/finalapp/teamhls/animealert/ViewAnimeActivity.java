package com.finalapp.teamhls.animealert;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Reminders;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.finalapp.teamhls.animealert.classes.WebViewController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import static android.provider.CalendarContract.Reminders.*;

public class ViewAnimeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String LOG_TAG = "My Log Tag";
    ArrayList<String> timeList;
    public LinkedHashMap<String, String> times = new LinkedHashMap<String, String>();
    Activity activity;
    int sYear, sMonth, sDay, eYear, eMonth, eDay;

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

    public void setTimes() {
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
    protected void onStart() {
        super.onStart();
        sp = (Spinner) findViewById(R.id.spinner);

        // Extract color list as keySet from colors hashmap
        timeList = new ArrayList<String>(times.keySet());
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, timeList);
        sp.setAdapter(myAdapter);

        if (notify_check_box.isChecked()) {
            Log.i(LOG_TAG, "notify check box is checked");
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


    public void onCheck(View view) {
        if (notify_check_box.isChecked()) {
            sp.setEnabled(true);
            sp.setClickable(true);
            ContentResolver cr = getContentResolver();

            //Intent intent = new Intent(Intent.ACTION_INSERT);
            //intent.setData(CalendarContract.Events.CONTENT_URI);
            String eventUriStr = "content://com.android.calendar/events";
            //intent.setType("vnd.android.cursor.item/event");
            ContentValues event = new ContentValues();
            event.put("calendar_id", 1);
            event.put("title", "THIS IS TEST EVENT");
            event.put("description", "Butt");
            event.put("eventLocation", "yoolo");
            event.put("eventTimezone", "UTC/GMT -8:00");

            // For next 1hr
            Calendar start = Calendar.getInstance();
            start.set(2016, 2, 12, 20, 57, 0);

            Calendar end = Calendar.getInstance();
            end.set(2016, 2, 12, 22, 0, 0);

            long startTime = start.getTimeInMillis();
            long endTime = end.getTimeInMillis();


            event.put("dtstart", startTime);
            event.put("dtend", endTime);
            //If it is bithday alarm or such kind (which should remind me for whole day) 0 for false, 1 for true
            // values.put("allDay", 1);
            event.put("eventStatus", 1);
            event.put("hasAlarm", 0);



          /*  intent.putExtra(CalendarContract.Events.TITLE, "Learn Android");
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home suit home");
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Download Examples");
            GregorianCalendar calDate = new GregorianCalendar(2012, 10, 02);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    calDate.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                    calDate.getTimeInMillis());
            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");*/

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
            reminderValues.put("minutes", 5);
            // Alert Methods: Default(0), Alert(1), Email(2), SMS(3)
            reminderValues.put("method", 1);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues); //Uri reminderUri =

          /*  ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(Reminders.MINUTES, 15);
            values.put(Reminders.EVENT_ID, eventID);
            values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
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
            Uri uri = cr.insert(Reminders.CONTENT_URI, values);*/

            //startActivity(intent);



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