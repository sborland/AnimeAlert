package com.finalapp.teamhls.animealert.classes;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by Henry on 3/7/2016.
 */
public class DownloadCover {
    private static final String webSiteURL = "http://myanimelist.net/anime/";
    private static final String folderPath = System.getProperty("user.dir");

    public static void getCover(int id) {
        try {
            String mal_id = Integer.toString(id);
            String newUrl = webSiteURL.concat(mal_id);
            Document doc = Jsoup.connect(newUrl).get();

            //Get all elements with img tag
            Elements img = doc.getElementsByTag("img");
            String src = img.first().absUrl("src");

            int indexname = src.lastIndexOf("/");
            String name = src.substring(indexname, src.length());

            //Open a URL Stream
            URL url = new URL(src);
            InputStream in = url.openStream();

            String filename = folderPath + "/" + mal_id + ".jpg";
            OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));

            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            System.err.println("There was an error.");
            Logger.getLogger(DownloadCover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
