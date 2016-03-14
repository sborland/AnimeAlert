package com.finalapp.teamhls.animealert.classes;

/**
 * Created by sab on 3/13/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

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

public class LoadingTask extends AsyncTask<String, Integer, Integer> {
    public static String LOG_TAG = "My Log Tag";
    private String userDB;
    File currentDB;
    AnimeDB currentChart;
    List<AnimeShow> BList = new ArrayList<AnimeShow>();
    List<AnimeChart> CList =new ArrayList<AnimeChart>();
    boolean updateDB = false;
    int prog = 0;
    ProgressDialog progressDialog;



    public interface LoadingTaskFinishedListener {
        void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
    }

    // This is the progress bar you want to update while the task is in progress
    private final ProgressBar progressBar;
    // This is the listener that will be told when this task is finished
    private final LoadingTaskFinishedListener finishedListener;
    private final Context context;

    /**
     * A Loading task that will load some resources that are necessary for the app to start
     * @param progressBar - the progress bar you want to update while the task is in progress
     * @param finishedListener - the listener that will be told when this task is finished
     */
    public LoadingTask(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener, Context context) {
        this.progressBar = progressBar;
        this.finishedListener = finishedListener;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        //Log.i("Tutorial", "Starting task with url: "+params[0]);
        currentDB = this.context.getDatabasePath("currentChart.db");
        Log.i(LOG_TAG, currentDB.getAbsolutePath());
        Log.i(LOG_TAG, (currentDB.exists()) + "");
        if (!currentDB.exists()) {
            currentChart = new AnimeDB(this.context);
            Log.i(LOG_TAG, "CREATED DATABASE");
        } else {
            Log.i(LOG_TAG, "FOUND DATABASE");
            updateDB = true;
            currentChart = new AnimeDB(this.context);
        }
        CreateRetrofit retro = new CreateRetrofit();
        Retrofit retrofit = retro.accessService(params[0]);

        GetBasicChart(retrofit);
        GetRawChart(retrofit);

        if (resourcesDontAlreadyExist()) {
           downloadResources();

        }


        // Perhaps you want to return something to your post execute
        return 1234;
    }

    private boolean resourcesDontAlreadyExist() {
        // Here you would query your app's internal state to see if this download had been performed before
        // Perhaps once checked save this in a shared preference for speed of access next time
        return true; // returning true so we show the splash every time
    }


    private void downloadResources( ) {
        // We are just imitating some process thats takes a bit of time (loading of resources / downloading)

        int count = 10;
        for (int i = 0; i < count; i++) {

            // Update the progress bar after every step
            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);

            // Do some long loading things
            try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
        }
        }



    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }



    public void GetImgSum(AnimeChart anime){

        //int size = CList.size();
        Log.i(LOG_TAG, "SIZE: "+ CList.size());
        //int i =0;
        //for (AnimeChart anime :CList){
           // int progress = (int) ((i / (float) size) * 100);
           // prog = progress;
           // Log.i(LOG_TAG, "Progress:" + progress);
            String[] imgsum = DownloadCover.getCover(anime.malNum);
            anime.setImg(imgsum[0]);
            anime.setSum(imgsum[1]);
            if (updateDB) {
                currentChart.update(anime);
            } else {
                currentChart.insert(anime);
            }
            //i++;

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
                    Log.i(LOG_TAG, "SERVER GOOD RAWDATA. Result: " + response.body());

                    for (AnimeShow y : BList) {
                        String ShowName = y.getName().replace(" (Premiere)", "");
                        // Log.i(LOG_TAG, "Before: "+ ShowName);
                        for (Item z : response.body().getItems()) {
                            if (z.getName().equals(ShowName)) {
                                //ADD TO DATABASE
                                AnimeChart anime = new AnimeChart();
                                anime.setTitle(ShowName);
                                anime.setAirDate(Long.valueOf(z.getAirdateU()));
                                anime.setIsShort(y.getShort().toString());
                                anime.setSimulCast(z.getSimulcast());
                                anime.setCurrEp(y.getCtr());
                                anime.setMalNum(Integer.parseInt(z.getMALID()));
                                if (updateDB) {
                                    currentChart.update(anime);
                                } else {
                                    currentChart.insert(anime);
                                }
                            }
                        }


                    }

                    //ArrayList<HashMap<String, String>> animelist = currentChart.getAnimeChart();
                    //Log.i(LOG_TAG, "size of database: " + animelist.size());
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
                        isItInList = false;
                        if (x.getUtime() >= CurrentDate && MonthDate >= x.getUtime()) {
                            for (AnimeShow y : BList){
                                if (y.getName().equals(x.getName())){
                                    if (x.getCtr() > y.getCtr()) {
                                        y.setCtr(x.getCtr());
                                       // Log.i(LOG_TAG, " Update Show: " + x.getName() + "Episode:" + x.getCtr());
                                    }
                                    isItInList= true;
                                }
                            }
                            if(!isItInList){
                                BList.add(x);
                                //Log.i(LOG_TAG, "Add Show: " + x.getName() + "Episode:" + x.getCtr());

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

    public interface AnimeBasicService {
        @GET("/export.php?type=json&src=episodes")
        Call<List<AnimeShow>> getAnimeBasicData();
    }

    public interface AnimeRawService {
        @GET("/export.php?type=json&src=raw")
        Call<AnimeRaw> getAnimeRawData();
    }
}