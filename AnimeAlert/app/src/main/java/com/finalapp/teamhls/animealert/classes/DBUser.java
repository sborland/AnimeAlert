package com.finalapp.teamhls.animealert.classes;

/**
 * Created by HuiShi on 3/11/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUser  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.

    //database name
    private static final String DATABASE_NAME = "userChart.db";
    private static final int DATABASE_VERSION = 1;

    public DBUser(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_USERCHART = "CREATE TABLE " + UserChart.TABLE  + "("
                + UserChart.KEY_malNum  + " INTEGER ,"
                + UserChart.KEY_title + " TEXT, "
                + UserChart.KEY_airDate + " INTEGER, "
                + UserChart.KEY_simulCast + " TEXT, "
                + UserChart.KEY_isShort + " TEXT, "
                + UserChart.KEY_userTime + "INTEGER"
                + UserChart.KEY_currEp + " INTEGER )";

        db.execSQL(CREATE_TABLE_USERCHART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + UserChart.TABLE);

        // Create tables again
        onCreate(db);

    }

}
