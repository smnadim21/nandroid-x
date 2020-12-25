package com.smnadim21.nandroidx;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.smnadim21.nadx.activity.InternetActivity;
import com.smnadim21.nadx.api.ApiClient;
import com.smnadim21.nadx.api.GetResponse;
import com.smnadim21.nadx.api.NetworkCall;

import org.json.JSONObject;

import retrofit2.Response;

public class MainActivity extends InternetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiClient.connect().login(
                "abc",
                "abc",
                "abc"
        ).enqueue(new NetworkCall(MainActivity.this, new GetResponse() {
            @Override
            public void onResponse(int code, String msg, JSONObject object, Response response) {

                Log.e("GetResponse", new GsonBuilder().setPrettyPrinting().create().toJson(object));

                //Log.e("GetResponse", ">>>>" + code+ " " + msg+ " " + object.toString() + response.toString());

            }

            @Override
            public void onErrorResponse(int code, String msg, JSONObject object, Response response) {

            }

            @Override
            public void onInternalError(int code, String msg, Response response) {

            }

            @Override
            public void onFailure(String msg, Throwable t) {

            }
        }));
    }
}
