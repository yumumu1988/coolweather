package com.example.yumumu.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 2/5/2018.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
