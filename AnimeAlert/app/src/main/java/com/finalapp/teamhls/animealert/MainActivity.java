package com.finalapp.teamhls.animealert;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.finalapp.teamhls.animealert.classes.AnimeChart;
import com.finalapp.teamhls.animealert.classes.AnimeDB;
import com.finalapp.teamhls.animealert.classes.CreateRetrofit;
import com.finalapp.teamhls.animealert.response.AnimeShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //intializes layout variables
        enterButton = (Button) findViewById(R.id.EnterButton);
        enterButton.setOnClickListener(this);


        //creates the retrofit and acesses it using the url
        String url = "http://www.senpai.moe/";
        CreateRetrofit retro = new CreateRetrofit();
        Retrofit retrofit = retro.accessService(url);
        AnimeDB currentChart = new AnimeDB(this, "currentChart.db");
        AnimeChart anime = new AnimeChart();
        GetBasicChart(retrofit);

    }

    ///call this method if you want to test getting the server info
    public void GetBasicChart(Retrofit retrofit){
            AnimeService service = retrofit.create(AnimeService.class);
            Call<List<AnimeShow>> AnimeSearch= service.getAnimeData();


            //Call retrofit asynchronously
            AnimeSearch.enqueue(new Callback<List<AnimeShow>>() {
                @Override
                public void onResponse(Response<List<AnimeShow>> response) {
                    int httpCheck = response.code();
                    //500 is server error while 200 is a-okay
                    Log.i(LOG_TAG, "Code is: " + response.code());
                    if (httpCheck != 200) {
                        //Display server error screen
                        Log.i(LOG_TAG, "SERVER ERROR");

                    } else {
                        //check if the result is 'error' or 'ok'
                        Log.i(LOG_TAG, "SERVER GOOD. Result: "+ response.body());
                        //Gets the current date and the dates for next two months
                        Long CurrentDate = System.currentTimeMillis()/1000;
                        Long MonthDate = CurrentDate+(604800*8);
                        //Grabs all airing anime between now and next two months
                        List<AnimeShow> myList = new ArrayList<AnimeShow>();
                        for(AnimeShow x: response.body()) {
                            if (x.getUtime() >= CurrentDate && MonthDate>= x.getUtime()) {
                                if (!myList.contains(x)) {
                                    myList.add(x);
                                } else {
                                    //only updates if there is a more current episode
                                    AnimeShow old = (myList.get(myList.indexOf(x)));
                                    if (x.getCtr() > old.getCtr()) {
                                        old.setCtr(x.getCtr());
                                    }
                                }
                            }
                        }
                        /////////////////Need to create function that puts myList into database yaaa

                        /* DEBUG
                         String ts = CurrentDate.toString();
                        for(AnimeShow y:myList){
                            Log.i(LOG_TAG, "title: " + y.getName() +"episode: "+ y.getCtr());

                        }
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


    public interface AnimeService {
        @GET("/export.php?type=json&src=episodes")
        Call<List<AnimeShow>> getAnimeData();
    }

}
