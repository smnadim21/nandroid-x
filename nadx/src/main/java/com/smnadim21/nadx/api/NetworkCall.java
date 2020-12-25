package com.smnadim21.nadx.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.smnadim21.nadx.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall implements Callback<String> {
    Activity activity;
    String tag = "NetworkCall";
    GetResponse getResponse;

    public NetworkCall(Activity activity) {
        this.activity = activity;
        tag = activity.getLocalClassName();
    }

    public NetworkCall(Activity activity, GetResponse getResponse) {
        this.activity = activity;
        tag = activity.getLocalClassName();
        this.getResponse = getResponse;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.e(tag, response.toString());
        if (response.body() != null && response.code() == 200) {
            Log.e(tag, response.body());
            try {
                JSONObject object = new JSONObject(response.body());
                Log.e(tag, getPrettyJson(response.body()));
                String message = object.optString("message", "empty message from server");

                getResponse.onResponse(
                        response.code(),
                        message,
                        object,
                        response);


            } catch (JSONException e) {
                e.printStackTrace();
                getResponse.onInternalError(
                        response.code(),
                        e.getMessage(),
                        response);
            }


            snackOK(activity,
                    "RESPONSE_OK",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
        } else {
            if (response.errorBody() != null) {
                try {
                    final String err = response.errorBody().string();
                    Log.e(tag, err);
                    Log.e(tag, getPrettyJson(err));
                    JSONObject error = new JSONObject(err);
                    getResponse.onErrorResponse(
                            response.code(),
                            error.optString("message", "no message from server"),
                            error,
                            response);

                    if (error.has("message")) {
                        snackErr(activity
                                , error.getString("message"),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showError(activity, getPrettyJson(err));
                                    }
                                });
                    }
                } catch (IOException e) {
                    getResponse.onInternalError(
                            response.code(),
                            e.getMessage(),
                            response);
                    e.printStackTrace();
                } catch (JSONException e) {
                    getResponse.onInternalError(
                            response.code(),
                            e.getMessage(),
                            response);
                    e.printStackTrace();
                }

            }
        }


    }


    @Override
    public void onFailure(Call<String> call, final Throwable t) {
        Log.e(tag, t.getMessage());
        getResponse.onFailure(
                t.getMessage(),
                t);
        snackErr(activity
                , t.getMessage(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showError(t.getMessage());
                    }
                });

    }

    private String getPrettyJson(String text) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(text));
    }

    void snackErr(Activity activity,
                  final String mainTextString,
                  View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red_900));
        snackbar.show();
    }

    void snackOK(Activity activity,
                 final String mainText,
                 View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainText,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.green_900));
        snackbar.show();
    }

    private static void showError(final Activity context, String errString) {
        final Dialog dialog = new Dialog(context, R.style.AppThemeAction);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_error);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView error = dialog.findViewById(R.id.error);
        error.setText(errString);
        dialog.show();
    }

    private void showError(String errString) {
        final Dialog dialog = new Dialog(activity, R.style.AppThemeAction);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_error);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView error = dialog.findViewById(R.id.error);
        error.setText(errString);
        dialog.show();
    }
}
