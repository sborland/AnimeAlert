package com.finalapp.teamhls.animealert.classes;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.finalapp.teamhls.animealert.R;
import com.finalapp.teamhls.animealert.classes.DownloadCover;

import java.io.InputStream;

public class AnimeListView extends ArrayAdapter<String> {
    public static String LOG_TAG = "My Log Tag";

    private final Activity context;

    private final String[] title;
    private final String[] summary;
    private final String[] imgurl;
    // private final Integer[] imageId;
    public AnimeListView(Activity context,
                      String[] title, String[] summary, String[] imgurl){//,Integer[] imageId) {
        super(context, R.layout.list_single, summary);
        this.context = context;
        this.title = title;
        this.summary= summary;
        this.imgurl = imgurl;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtSummary = (TextView) rowView.findViewById(R.id.txt);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtname);
        new DownloadImageTask ((ImageView) rowView.findViewById(R.id.img)).execute(imgurl[position]);


        txtTitle.setText(title[position]);
        txtSummary.setText(summary[position]);
   return rowView;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error!");
                e.printStackTrace();
            }


            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
