package com.finalapp.teamhls.animealert.classes;

/**
 * Created by HuiShi on 3/6/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.

    //database name
    private static final String DATABASE_NAME = "currentChart.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_ANIMECHART = "CREATE TABLE " + AnimeChart.TABLE  + "("
                + AnimeChart.KEY_malNum  + " INTEGER ,"
                + AnimeChart.KEY_title + " TEXT, "
                + AnimeChart.KEY_airDate + " INTEGER, "
                + AnimeChart.KEY_simulCast + " TEXT, "
                + AnimeChart.KEY_isShort + " TEXT, "
                + AnimeChart.KEY_currEp + " INTEGER, "
                + AnimeChart.KEY_sum + " TEXT, "
                + AnimeChart.KEY_img+ " TEXT )";

        db.execSQL(CREATE_TABLE_ANIMECHART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + AnimeChart.TABLE);

        // Create tables again
        onCreate(db);

    }

}
