package com.finalapp.teamhls.animealert.classes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * DownloadCover Asynchronously uses Jsoup to grab info from each anime's anime list
 */
public class DownloadCover {
    public static String LOG_TAG = "My Log Tag";
    private static final String webSiteURL = "http://myanimelist.net/anime/";
    static String finImage;
    static String finSummary;

    public static String[] getCover(int id) {
            final String mal_id = Integer.toString(id);
            final String newUrl = webSiteURL.concat(mal_id);

       //Thread to grab the needed data

        Runnable run = new  Runnable() {
            @Override
            public void run() {
                try {
                    //grabs the url for the website and summary
                    Document doc = Jsoup.connect(newUrl).get();
                  //  Log.i(LOG_TAG, " Anime's site "+ doc.toString());

                    Elements url = doc.select("a[href]");
                    Elements sum = doc.select("meta[property=og:description]");
                   // Log.i(LOG_TAG, " Anime's site "+ url.attr("href"));
                    finSummary= sum.attr("content");

                    //grabs anime's title image
                   String picUrl = url.attr("href")+"/pics";
                    Document ndoc = Jsoup.connect(picUrl).get();
                    Elements im = ndoc.select("meta[property=og:image]");
                    //finImage = null;

                    finImage = im.attr("content").toString();
                    //Log.i(LOG_TAG,"finImage "+ finImage);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Unable to grab image url of "+mal_id);

                }
            }
        };
        Thread t = new Thread(run);
        t.start();
        try{
            t.join();
        }catch (Exception e){
            Log.e(LOG_TAG, "Thread exception in viewing anime");
        }
        //returns image url and summary of show
        return new String[] {finImage,finSummary};

    }
    

}
