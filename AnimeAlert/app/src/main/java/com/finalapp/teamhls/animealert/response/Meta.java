package com.finalapp.teamhls.animealert.response;

/**
 * Created by HuiShi on 3/11/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("start_u")
    @Expose
    private Integer startU;
    @SerializedName("tz")
    @Expose
    private Tz tz;

    /**
     *
     * @return
     *     The season
     */
    public String getSeason() {
        return season;
    }

    /**
     *
     * @param season
     *     The season
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     *
     * @return
     *     The start
     */
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start
     *     The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return
     *     The startU
     */
    public Integer getStartU() {
        return startU;
    }

    /**
     *
     * @param startU
     *     The start_u
     */
    public void setStartU(Integer startU) {
        this.startU = startU;
    }

    /**
     *
     * @return
     *     The tz
     */
    public Tz getTz() {
        return tz;
    }

    /**
     *
     * @param tz
     *     The tz
     */
    public void setTz(Tz tz) {
        this.tz = tz;
    }

}
