package com.finalapp.teamhls.animealert.response;

/**
 * Created by HuiShi on 3/11/2016.
 */


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("continuing")
    @Expose
    private Boolean continuing;
    @SerializedName("unique")
    @Expose
    private Boolean unique;

    /**
     *
     * @return
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     *     The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     *
     * @return
     *     The continuing
     */
    public Boolean getContinuing() {
        return continuing;
    }

    /**
     *
     * @param continuing
     *     The continuing
     */
    public void setContinuing(Boolean continuing) {
        this.continuing = continuing;
    }

    /**
     *
     * @return
     *     The unique
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     *
     * @param unique
     *     The unique
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

}
