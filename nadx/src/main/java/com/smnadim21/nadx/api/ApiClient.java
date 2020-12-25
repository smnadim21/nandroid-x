package com.smnadim21.nadx.api;




import com.smnadim21.nadx.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static final String DEV_URL = "https://army-pharma.datasysbd.net/";
    private static final String PRODUCTION_URL = "https:/army-pharma.datasysbd.net/";
    public static final String BASE_URL = BuildConfig.DEBUG ? DEV_URL : PRODUCTION_URL;

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
                //.addConverterFactory(GsonConverterFactory.create())
                .build().create(Routes.class);
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }
}