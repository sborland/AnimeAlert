package com.finalapp.teamhls.animealert.classes;
/**
 * Created by HuiShi on 3/6/2016.
 */
public class AnimeChart {
        // Labels table name
        public static final String TABLE = "Current";

        // Labels Table Columns names
        public static final String KEY_malNum = "malNum";
        public static final String KEY_title = "title";
        public static final String KEY_airDate = "airDate";
        public static final String KEY_simulCast = "simulCast";
        public static final String KEY_isShort = "isShort";
        public static final String KEY_currEp = "currEp";

        // property help us to keep data
        public int malNum;
        public String title;
        public Long airDate;
        public String simulCast;
        public String isShort;
        public int currEp;

        public void setMalNum(int malNum) {
                this.malNum = malNum;
        }
        public void setTitle(String title) {
                this.title = title;
        }
        public void setAirDate(Long airDate) {
                this.airDate = airDate;
        }
        public void setSimulCast(String simulCast) {
                this.simulCast = simulCast;
        }
        public void setIsShort(String isShort) {
                this.isShort = isShort;
        }
        public void setCurrEp(int currEp) {
                this.currEp = currEp;
        }







}
