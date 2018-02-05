package com.example.yumumu.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 2/5/2018.
 */

public class Basic {

    @SerializedName("city")
    private String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
