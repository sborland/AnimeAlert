package com.finalapp.teamhls.animealert;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.Button;
        import android.widget.NumberPicker;

        import com.finalapp.teamhls.animealert.classes.WebViewController;

public class ViewAnimeActivity extends AppCompatActivity implements View.OnClickListener {
    public static String LOG_TAG = "My Log Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anime);
        Log.i(LOG_TAG, "In ViewAnime");

        //grab the malNum from previous chart
        Bundle b = getIntent().getExtras();
        String malNum = b.getString("malNum");
        String url = "http://myanimelist.net/anime/"+malNum;

        Button retButton = (Button) findViewById(R.id.ReturnButton);
        retButton.setOnClickListener(this);

        //loads up webview
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.loadUrl(url);

        //number picker set up
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(12);
        np.setWrapSelectorWheel(false);

    }


    @Override
    public void onClick(View view) {
        String vStr = (String) view.getTag();
        Log.i(LOG_TAG, vStr);
        if (vStr.equalsIgnoreCase("return")) {
            finish();
        }
    }
}
