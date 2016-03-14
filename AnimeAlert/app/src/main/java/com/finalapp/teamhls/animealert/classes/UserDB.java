package com.finalapp.teamhls.animealert.classes;

/**
 * Created by HuiShi on 3/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by HuiShi on 3/6/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDB {
    private DBUser dbUser;
    public static String LOG_TAG = "My Log Tag";

    public UserDB(Context context) {
        dbUser = new DBUser(context);
    }

    public void insert(UserChart chart) {

        //Open connection to write data
        SQLiteDatabase db = dbUser.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserChart.KEY_malNum, chart.malNum);
        values.put(UserChart.KEY_title,chart.title);
        values.put(UserChart.KEY_airDate, chart.airDate);
        values.put(UserChart.KEY_simulCast, chart.simulCast);
        values.put(UserChart.KEY_isShort, chart.isShort);
        values.put(UserChart.KEY_userTime, chart.userTime);
        values.put(UserChart.KEY_currEp, chart.currEp);


        // Inserting Row
        db.insert(UserChart.TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void delete(int malNum) {

        SQLiteDatabase db = dbUser.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(UserChart.TABLE, UserChart.KEY_malNum + "= ?", new String[] { String.valueOf(malNum) });
        db.close(); // Closing database connection
    }

    public void update(UserChart chart) {

        SQLiteDatabase db = dbUser.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserChart.KEY_malNum, chart.malNum);
        values.put(UserChart.KEY_title,chart.title);
        values.put(UserChart.KEY_airDate, chart.airDate);
        values.put(UserChart.KEY_simulCast, chart.simulCast);
        values.put(UserChart.KEY_isShort, chart.isShort);
        values.put(UserChart.KEY_userTime, chart.userTime);
        values.put(UserChart.KEY_currEp, chart.currEp);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(UserChart.TABLE, values, UserChart.KEY_malNum + "= ?", new String[] { String.valueOf(chart.malNum) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getUserChart() {
        //Open connection to read only
        SQLiteDatabase db = dbUser.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                UserChart.KEY_title + "," +
                UserChart.KEY_malNum + "," +
                UserChart.KEY_airDate + "," +
                UserChart.KEY_simulCast + "," +
                UserChart.KEY_isShort + "," +
                UserChart.KEY_userTime + "," +
                UserChart.KEY_currEp +
                " FROM " + UserChart.TABLE;
        ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> anime = new HashMap<String, String>();
                anime.put("title", cursor.getString(cursor.getColumnIndex(UserChart.KEY_title)));
                anime.put("malNum", cursor.getString(cursor.getColumnIndex(UserChart.KEY_malNum)));
                anime.put("airDate", cursor.getString(cursor.getColumnIndex(UserChart.KEY_airDate)));
                anime.put("simulCast", cursor.getString(cursor.getColumnIndex(UserChart.KEY_simulCast)));
                anime.put("userTime", cursor.getString(cursor.getColumnIndex(UserChart.KEY_userTime)));
                anime.put("isShort", cursor.getString(cursor.getColumnIndex(UserChart.KEY_isShort)));
                anime.put("currEp", cursor.getString(cursor.getColumnIndex(UserChart.KEY_currEp)));
                userList.add(anime);

            } while (cursor.moveToNext());
        }
        Log.i(LOG_TAG, userList.size() + "");

        cursor.close();
        db.close();

        return userList;

    }

    public UserChart getAnimeByMalNum(int malNum){
        SQLiteDatabase db = dbUser.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                UserChart.KEY_title + "," +
                UserChart.KEY_malNum + "," +
                UserChart.KEY_airDate + "," +
                UserChart.KEY_simulCast + "," +
                UserChart.KEY_isShort + "," +
                UserChart.KEY_currEp + "," +
                UserChart.KEY_userTime +
                " FROM " + UserChart.TABLE
                + " WHERE " +
                UserChart.KEY_malNum + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        UserChart chart = new UserChart();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(malNum) } );

        if (cursor.moveToFirst()) {
            do {
                chart.title =cursor.getString(cursor.getColumnIndex(UserChart.KEY_title));
                chart.malNum =cursor.getInt(cursor.getColumnIndex(UserChart.KEY_malNum));
                chart.airDate  =cursor.getLong(cursor.getColumnIndex(UserChart.KEY_airDate));
                chart.simulCast =cursor.getString(cursor.getColumnIndex(UserChart.KEY_simulCast));
                chart.isShort  =cursor.getString(cursor.getColumnIndex(UserChart.KEY_isShort));
                chart.currEp =cursor.getInt(cursor.getColumnIndex(UserChart.KEY_currEp));
                chart.userTime =cursor.getLong(cursor.getColumnIndex(UserChart.KEY_userTime));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chart;
    }

}