package com.finalapp.teamhls.animealert.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HuiShi on 3/6/2016.
 */
public class AnimeShow {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("utime")
    @Expose
    public Long utime;
    @SerializedName("ctr")
    @Expose
    public Integer ctr;
    @SerializedName("short")
    @Expose
    public Boolean _short;
    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The utime
     */
    public Long getUtime() {
        return utime;
    }

    /**
     *
     * @param utime
     * The utime
     */
    public void setUtime(Long utime) {
        this.utime = utime;
    }

    /**
     *
     * @return
     * The ctr
     */
    public Integer getCtr() {
        return ctr;
    }

    /**
     *
     * @param ctr
     * The ctr
     */
    public void setCtr(Integer ctr) {
        this.ctr = ctr;
    }

    /**
     *
     * @return
     * The _short
     */
    public Boolean getShort() {
        return _short;
    }

    /**
     *
     * @param _short
     * The short
     */
    public void setShort(Boolean _short) {
        this._short = _short;
    }
}
