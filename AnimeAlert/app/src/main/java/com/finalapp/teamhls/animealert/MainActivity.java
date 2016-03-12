package com.finalapp.teamhls.animealert;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.finalapp.teamhls.animealert.classes.AnimeChart;
import com.finalapp.teamhls.animealert.classes.AnimeDB;
import com.finalapp.teamhls.animealert.classes.CreateRetrofit;
import com.finalapp.teamhls.animealert.response.AnimeRaw;
import com.finalapp.teamhls.animealert.response.AnimeShow;
import com.finalapp.teamhls.animealert.response.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";
    private String userDB;
    File currentDB;
    Button enterButton;
    AnimeDB currentChart;
    List<AnimeShow> BList = new ArrayList<AnimeShow>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intializes layout variables
        enterButton = (Button) findViewById(R.id.EnterButton);
        enterButton.setOnClickListener(this);

        currentDB =getApplicationContext().getDatabasePath("currentChart.db");
        Log.i(LOG_TAG, currentDB.getAbsolutePath());
        Log.i(LOG_TAG,(currentDB.exists())+"");
        if(!currentDB.exists()){
            currentChart = new AnimeDB(this);
            Log.i(LOG_TAG, "CREATED DATABASE");
        }else {
            Log.i(LOG_TAG, "FOUND DATABASE");
            currentChart = new AnimeDB(this);
            ArrayList<HashMap<String, String>> animelist = currentChart.getAnimeChart();
            for (HashMap<String,String> x : animelist){
               for (Map.Entry entry : x.entrySet()){
                   Log.i(LOG_TAG,entry.getKey() +" "+ entry.getValue());
               }
            }
            Log.i(LOG_TAG, "TEST: " + currentChart.getAnimeByMalNum(31414).title);
        }

        //creates the retrofit and acesses it using the url
        String url = "http://www.senpai.moe/";
        CreateRetrofit retro = new CreateRetrofit();
        Retrofit retrofit = retro.accessService(url);
        GetBasicChart(retrofit);
        GetRawChart(retrofit);

    }

    public void GetRawChart(Retrofit retrofit){
        AnimeRawService service = retrofit.create(AnimeRawService.class);
        Call<AnimeRaw> AnimeSearch= service.getAnimeRawData();

        AnimeSearch.enqueue(new Callback<AnimeRaw>() {
            @Override
            public void onResponse(Response<AnimeRaw> response) {
                int httpCheck = response.code();
                //500 is server error while 200 is a-okay
                Log.i(LOG_TAG, "Code is: " + response.code());
                if (httpCheck != 200) {
                    //Display server error screen
                    Log.i(LOG_TAG, "SERVER ERROR RAWDATA");

                } else {
                    //check if the result is 'error' or 'ok'
                    Log.i(LOG_TAG, "SERVER GOOD RAWDATA. Result: " +response.body());
                    for (AnimeShow y: BList) {
                        String ShowName = y.getName().replace(" (Premiere)","");
                        // Log.i(LOG_TAG, "Before: "+ ShowName);
                        for(Item z: response.body().getItems()) {
                            if (z.getName().equals(ShowName)) {
                                //ADD TO DATABASE
                                AnimeChart anime = new AnimeChart();
                                anime.setTitle(ShowName);
                                anime.setAirDate(Long.valueOf(z.getAirdateU()));
                                anime.setIsShort(y.getShort().toString());
                                anime.setSimulCast(z.getSimulcast());
                                anime.setCurrEp(y.getCtr());
                                anime.setMalNum(Integer.parseInt(z.getMALID()));
                                currentChart.insert(anime);
                                //Log.i(LOG_TAG, "Got: " + ShowName + " MAL: " + z.getMALID());

                            }
                         }
                     }
                  }
            }
            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.i(LOG_TAG, "onFailure: " + t);
            }
        });



    }

    ///call this method if you want to test getting the server info
    public void GetBasicChart(Retrofit retrofit){
            AnimeBasicService service = retrofit.create(AnimeBasicService.class);
            Call<List<AnimeShow>> AnimeSearch= service.getAnimeBasicData();

            //Call retrofit asynchronously
            AnimeSearch.enqueue(new Callback<List<AnimeShow>>() {
                @Override
                public void onResponse(Response<List<AnimeShow>> response) {
                    int httpCheck = response.code();
                    //500 is server error while 200 is a-okay
                    Log.i(LOG_TAG, "Code is: " + response.code());
                    if (httpCheck != 200) {
                        //Display server error screen
                        Log.i(LOG_TAG, "SERVER ERROR BASICDATA");

                    } else {
                        //check if the result is 'error' or 'ok'
                        Log.i(LOG_TAG, "SERVER GOOD BASICDATA. Result: "+ response.body());
                        //Gets the current date and the dates for next two months
                        Long CurrentDate = System.currentTimeMillis()/1000;
                        Long MonthDate = CurrentDate+(604800*8);
                        //Grabs all airing anime between now and next two months
                        for(AnimeShow x: response.body()) {
                            if (x.getUtime() >= CurrentDate && MonthDate>= x.getUtime()) {
                                if (!BList.contains(x)) {
                                    BList.add(x);
                                } else {
                                    //only updates if there is a more current episode
                                    AnimeShow old = (BList.get(BList.indexOf(x)));
                                    if (x.getCtr() > old.getCtr()) {
                                        old.setCtr(x.getCtr());
                                    }
                                }
                            }
                        }
                        /* DEBUG
                         String ts = CurrentDate.toString();
                        for(AnimeShow y:myList){
                            Log.i(LOG_TAG, "title: " + y.getName() +"episode: "+ y.getCtr()); }
                        Log.i(LOG_TAG,ts+ " List size: "+myList.size());
                        */
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.i(LOG_TAG, "onFailure: " + t);
                }
            });


    }




    @Override
    public void onClick(View view){
        String vStr = (String) view.getTag();
        Log.i(LOG_TAG, vStr);
        if (vStr.equalsIgnoreCase("enter")) {
            Intent Menu= new Intent(MainActivity.this, MenuActivity.class);
            MainActivity.this.startActivity(Menu);
        }
    }


    public interface AnimeBasicService {
        @GET("/export.php?type=json&src=episodes")
        Call<List<AnimeShow>> getAnimeBasicData();
    }

    public interface AnimeRawService {
        @GET("/export.php?type=json&src=raw")
        Call<AnimeRaw> getAnimeRawData();
    }

}
