package com.finalapp.teamhls.animealert;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.DrawableRes;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.finalapp.teamhls.animealert.response.Hummingbird;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.prefs.Preferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class MainActivity extends AppCompatActivity {
    String title;
    public static String LOG_TAG = "My Log Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.senpai.moe/")
                .addConverterFactory(GsonConverterFactory.create())	//parse Gson string
                .client(httpClient)	//add logging
                .build();

        AnimeService service = retrofit.create(AnimeService.class);
        title ="Sushi Police";

        Call<Hummingbird> AnimeSearch= service.getAnimeData();


        //Call retrofit asynchronously
        AnimeSearch.enqueue(new Callback<Hummingbird>() {
            @Override
            public void onResponse(Response<Hummingbird> response) {
                int httpCheck = response.code();
                //500 is server error while 200 is a-okay
                Log.i(LOG_TAG, "Code is: " + response.code());
                if (httpCheck != 200) {
                    //Display server error screen
                    Log.i(LOG_TAG, "SERVER ERROR");

                } else {
                    //check if the result is 'error' or 'ok'
                    Log.i(LOG_TAG, "SERVER GOOD. Result: "+ response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
                Log.i(LOG_TAG, "onFailure: " + t);
            }
        });
    }



    public interface AnimeService {
        @GET("/export.php?type=json&src=episodes/")
        Call<Hummingbird> getAnimeData();

    }

}
