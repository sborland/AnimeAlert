package com.finalapp.teamhls.animealert;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.finalapp.teamhls.animealert.classes.CreateRetrofit;
import com.finalapp.teamhls.animealert.response.Hummingbird;

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

    }

    ///call this method if you want to test getting the server info
    public void testingServer(Retrofit retrofit){
            AnimeService service = retrofit.create(AnimeService.class);
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
        @GET("/export.php?type=json&src=episodes/")
        Call<Hummingbird> getAnimeData();
    }

}
