package com.smnadim21.nandroidx;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.smnadim21.nadx.activity.InternetActivity;
import com.smnadim21.nadx.api.BaseApiClient;
import com.smnadim21.nadx.api.GetResponse;
import com.smnadim21.nadx.api.NetworkCall;

import org.json.JSONObject;

import retrofit2.Response;

public class MainActivity extends InternetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  BaseApiClient.DEV_URL = "https:/army-pharma.datasysbd.net/";
        // BaseApiClient.PRODUCTION_URL = "https:/army-pharma.datasysbd.net/";
        BaseApiClient.BASE_URL = "https:/army-pharma.datasysbd.net/";

       BaseApiClient
                .connect()
                .login(
                        "abc@AA.cc",
                        "abc",
                        "abc"
                ).enqueue(new NetworkCall(MainActivity.this, new GetResponse() {
            @Override
            public void onResponse(int code, String msg, JSONObject object, Response response) {

            }

            @Override
            public void onErrorResponse(int code, String msg, JSONObject error, Response response) {

            }

            @Override
            public void onInternalError(int code, String msg, Response response) {

            }

            @Override
            public void onFailure(String msg, Throwable t) {

            }
        },true));
    }
}
