package com.smnadim21.nadx.api;


import com.smnadim21.nadx.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseApiClient {
    public static String DEV_URL = "https://example.datasysbd.net/";
    public static String PRODUCTION_URL = "https:/example.datasysbd.net/";
    public static String BASE_URL = BuildConfig.DEBUG ? DEV_URL : PRODUCTION_URL;

    public static Routes getGsonInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Routes.class);
    }

    public static Routes connect() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Routes.class);
    }


    public static String getDevUrl() {
        return DEV_URL;
    }

    public static String getProductionUrl() {
        return PRODUCTION_URL;
    }

    public BaseApiClient setDevUrl(String devUrl) {
        DEV_URL = devUrl;
        return this;
    }

    public BaseApiClient setProductionUrl(String productionUrl) {
        BaseApiClient.PRODUCTION_URL = productionUrl;
        return this;
    }


}