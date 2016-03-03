
package com.finalapp.teamhls.animealert.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Hummingbird{

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("utime")
        @Expose
        public Integer utime;
        @SerializedName("ctr")
        @Expose
        public Integer ctr;
        @SerializedName("short")
        @Expose
        public Boolean _short;

    }