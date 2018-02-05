package com.example.yumumu.coolweather.util;

import android.text.TextUtils;

import com.example.yumumu.coolweather.db.City;
import com.example.yumumu.coolweather.db.County;
import com.example.yumumu.coolweather.db.Province;
import com.example.yumumu.coolweather.gson.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2/5/2018.
 */

public class Utility {

    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
//                可以使用GSON
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//                City model中使用transient
//                Gson gson = new Gson();
                List<City> cityList = new ArrayList<>();
                Type type = new TypeToken<ArrayList<City>>(){}.getType();
                cityList = gson.fromJson(response, type);
                for (City city : cityList){
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                Gson gson = new Gson();
                List<County> countyList = new ArrayList<>();
                Type type = new TypeToken<ArrayList<County>>(){}.getType();
                countyList = gson.fromJson(response, type);
                for (County county : countyList){
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
