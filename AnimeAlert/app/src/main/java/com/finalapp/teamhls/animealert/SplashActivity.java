package com.finalapp.teamhls.animealert;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.util.Log;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalapp.teamhls.animealert.classes.AnimeChart;
import com.finalapp.teamhls.animealert.classes.AnimeDB;
import com.finalapp.teamhls.animealert.classes.CreateRetrofit;
import com.finalapp.teamhls.animealert.classes.DownloadCover;
import com.finalapp.teamhls.animealert.response.AnimeRaw;
import com.finalapp.teamhls.animealert.response.AnimeShow;
import com.finalapp.teamhls.animealert.response.Item;
import com.finalapp.teamhls.animealert.classes.UserDB;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class SplashActivity extends Activity {
    public static String LOG_TAG = "My Log Tag";
    File currentDB;
    File userDB;
    TextView loading;
    Retrofit retrofit;
    UserDB userChart;
    AnimeDB currentChart;
    List<AnimeChart> CList = new ArrayList<AnimeChart>();
    List<AnimeShow> BList = new ArrayList<AnimeShow>();
    boolean updateDB = false;


    boolean internet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.activity_splash);
        // Find the progress bar
        loading = (TextView) findViewById(R.id.LoadText);
        internet = checkPermissions();
        if (internet = false){
            Log.i(LOG_TAG, "NO INTERNET :(");
            loading.setText("No or low Internet connection Detected. Please connect to the internet and restart application.");


        }else {
            // Start your loading
            // LoadingTask task = new LoadingTask(progressBar,this,getApplicationContext());
            //task.execute("http://www.senpai.moe/"); // Pass in whatever you need a url is just an example we don't use it in this tutorial
            userDB =getApplicationContext().getDatabasePath("userChart.db");
            currentDB = getApplicationContext().getDatabasePath("currentChart.db");
            userChart = new UserDB(this);
            Log.i(LOG_TAG, currentDB.getAbsolutePath());
            Log.i(LOG_TAG, (currentDB.exists()) + "");
            if (!currentDB.exists()) {
                currentChart = new AnimeDB(this);
                Log.i(LOG_TAG, "CREATED DATABASE");
            } else {
                Log.i(LOG_TAG, "FOUND DATABASE");
                //updateDB = true;
                currentChart = new AnimeDB(this);
            }
            Log.i(LOG_TAG, "TEST: " + currentChart.getAnimeByMalNum(31414).title);
            CreateRetrofit retro = new CreateRetrofit();
            retrofit = retro.accessService("http://www.senpai.moe/");

            GetBasicChart(retrofit);
            GetRawChart(retrofit);
            // LoadEverything();
        }


    }

    // This is the callback for when your async task has finished
  //  @Override
  //  public void onTaskFinished() {
   //     completeSplash();
  //  }

    public void completeSplash(){

        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    public void LoadEverything(){

        ArrayList<HashMap<String, String>> animelist = currentChart.getAnimeChart();
        Log.i(LOG_TAG, "Size" + animelist.size());

        for (HashMap<String,String> x : animelist){
            for (Map.Entry entry : x.entrySet()){
                Log.i(LOG_TAG,entry.getKey() +" "+ entry.getValue());
            }
        }


    }

    public void LoadImages() {

        Log.i(LOG_TAG,"FINISHED");



    }
    public void GetRawChart(final Retrofit retrofit){
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
                    Log.i(LOG_TAG, "SERVER GOOD RAWDATA. Result: " + response.body());
                    int count = BList.size();
                    for (AnimeShow y : BList) {
                        String ShowName = y.getName().replace(" (Premiere)", "");
                        // Log.i(LOG_TAG, "Before: "+ ShowName);
                        for (Item z : response.body().getItems()) {
                            if (z.getName().equals(ShowName)) {
                                //ADD TO DATABASE
                                Log.i(LOG_TAG, "STARTED: " + ShowName + " MAL: " + z.getMALID());
                                AnimeChart anime = new AnimeChart();
                                anime.setTitle(ShowName);
                                anime.setAirDate(Long.valueOf(z.getAirdateU()));
                                anime.setIsShort(y.getShort().toString());
                                anime.setSimulCast(z.getSimulcast());
                                anime.setCurrEp(y.getCtr());
                                anime.setMalNum(Integer.parseInt(z.getMALID()));
                                String[] imgsum = DownloadCover.getCover(anime.malNum);
                                anime.setImg(imgsum[0]);
                                //Log.i(LOG_TAG, "PRINT THIS: " + imgsum[0]);
                                anime.setSum(imgsum[1]);
                                //boolean test =!(currentChart.getAnimeByMalNum(anime.malNum).airDate==null);
                                //Log.i(LOG_TAG,"Is it in database already?:" + test);
                                if (updateDB && !(currentChart.getAnimeByMalNum(anime.malNum)==null)) {
                                    currentChart.update(anime);
                                } else {
                                    currentChart.insert(anime);
                                }

                                Log.i(LOG_TAG, "Got: " + ShowName + " MAL: " + z.getMALID());

                            }
                        }
                    }
                    LoadImages();
                    completeSplash();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.i(LOG_TAG, "onFailure: " + t);
                GetRawChart(retrofit);
            }
        });



    }



    ///call this method if you want to test getting the server info
    public void GetBasicChart(final Retrofit retrofit){
        AnimeBasicService service = retrofit.create(AnimeBasicService.class);
        final Call<List<AnimeShow>> AnimeSearch= service.getAnimeBasicData();


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
                    Log.i(LOG_TAG, "SERVER GOOD BASICDATA. Result: " + response.body());
                    //Gets the current date and the dates for next two months
                    Long CurrentDate = System.currentTimeMillis() / 1000;
                    Long MonthDate = CurrentDate + (604800 * 8);

                    //Grabs all airing anime between now and next two months
                    boolean isItInList;
                    int count = response.body().size();
                    for (AnimeShow x : response.body()) {
                        String ShowName = x.getName().replace(" (Premiere)", "");
                        isItInList = false;
                        if (x.getUtime() >= CurrentDate && MonthDate >= x.getUtime()) {
                            for (AnimeShow y : BList) {
                                if (y.getName().equals(ShowName)) {
                                    if (x.getCtr() > y.getCtr()) {
                                        y.setCtr(x.getCtr());
                                        //Log.i(LOG_TAG, " Update Show: " + x.getName() + "Episode:" + x.getCtr());
                                    }
                                    isItInList = true;
                                }
                            }
                            if (!isItInList) {
                                x.setName(ShowName);
                                BList.add(x);
                                // Log.i(LOG_TAG, "Add Show: " + x.getName() + "Episode:" + x.getCtr());

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
                GetBasicChart(retrofit);
            }
        });



    }

    //Checks if user has the connections/permissions
    public Boolean checkPermissions(){
        //check if user is connected to internet
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            internet = true;
        } else {
            internet = false;
        }
        return internet;
    }


    private void startApp() {
        Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
        startActivity(intent);
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


