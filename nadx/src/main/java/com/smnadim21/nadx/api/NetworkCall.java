package com.smnadim21.nadx.api;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.smnadim21.nadx.R;
import com.smnadim21.nadx.tools.NandX;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall implements Callback<String> {
    Activity activity;
    String tag = "NetworkCall";
    String okMsg = "";
    GetResponse getResponse;
    View progressView;
    boolean progress;

  /*  public NetworkCall(Activity activity) {
        this.activity = activity;
        tag = activity.getLocalClassName();
    }*/

    public NetworkCall(Activity activity, GetResponse getResponse) {
        this.activity = activity;
        tag = activity.getLocalClassName();
        this.getResponse = getResponse;
    }

    public NetworkCall(Activity activity, GetResponse getResponse, View progressView) {
        this.activity = activity;
        tag = activity.getLocalClassName();
        this.getResponse = getResponse;
        this.progressView = progressView;
        showProgressView();
    }

    public NetworkCall(Activity activity, GetResponse getResponse, boolean progress) {
        this.activity = activity;
        tag = activity.getLocalClassName();
        this.getResponse = getResponse;
        this.progress = progress;
        if (progress) {
            View view = LayoutInflater.from(activity).inflate(R.layout.loading, null);
            this.progressView = view;
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.gravity = Gravity.CENTER;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSPARENT;
            activity.getWindowManager().addView(view, params);
            showProgressView();
        }
    }


    @Override
    public void onResponse(@NotNull Call<String> call, Response<String> response) {
        hideProgressView();
        Log.e(tag, response.toString());
        if (response.body() != null &&
                (response.code() == 200 || response.code() == 201)) {
            Log.e(tag, response.body());
            try {
                JSONObject object = new JSONObject(response.body());
                Log.e(tag, getPrettyJson(response.body()));
                String message = object.optString("message", "empty message from server");
                okMsg = message;

                getResponse.onResponse(
                        response.code(),
                        message,
                        object,
                        response);


            } catch (final JSONException e) {
                e.printStackTrace();
                getResponse.onInternalError(
                        response.code(),
                        e.getMessage(),
                        response);

                snackErr(activity
                        , e.getMessage(),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showError(activity, getPrettyJson(e));
                            }
                        });
            }


          /*  snackOK(activity,
                    okMsg,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/
        } else if (response.code() == 400) {
            if (response.errorBody() != null) {
                try {
                    final String err = response.errorBody().string();

                    Log.e("onErrorResponse", err);
                    JSONObject validation = new JSONObject(err);
                    Log.e("onErrorResponse", new Gson().toJson(validation));

                    getResponse.onErrorResponse(
                            response.code(),
                            validation.optString("message", "no message from server"),
                            validation,
                            response);

                    snackErr(activity
                            , response.code() + " " + validation.optString("message"),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showError(activity, getPrettyJson(err));
                                }
                            });

                    if (validation.has("errors")) {
                        JSONObject errors = validation.getJSONObject("errors");

                        Iterator<String> keys = errors.keys();
                        if (keys.hasNext()) {
                            String currentKey = keys.next();
                            if (errors.has(currentKey)) {
                                Log.e("error>>", currentKey);
                                snackErr(activity
                                        , //response.code()
                                        //   + " "
                                        //  + validation.optString("message") +
                                        errors.getJSONArray(currentKey).getString(0)
                                        ,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showError(activity, getPrettyJson(err));
                                            }
                                        });


                            }
                        }


                    }


                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    getResponse.onInternalError(
                            response.code(),
                            e.getMessage(),
                            response);

                    snackErr(activity
                            , e.getMessage(),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showError(activity, getPrettyJson(e));
                                }
                            });
                }
            }
        } else {
            if (response.errorBody() != null) {
                try {
                    final String err = response.errorBody().string();
                    Log.e(tag + " onErrorResponse>>", getPrettyJson(err));
                    JSONObject error = new JSONObject(err);

                   /* if (response.code() == 401) {
                        ImportantTools.getSession(activity);
                    }*/

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
    public void onFailure(@NotNull Call<String> call, final Throwable t) {
        hideProgressView();
        Log.e(tag + " onFailure", t.getMessage());
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

    private String getPrettyJson(Object obj) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }

    private void snackErr(Activity activity,
                          final String mainTextString,
                          View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("MORE", listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red_900));
        TextView st = snackbar.getView().findViewById(R.id.snackbar_text);
        // st.setTypeface(ResourcesCompat.getFont(activity, R.font.fira_mono));
        st.setPadding(5, 5, 5, 5);
        st.setSingleLine(false);
        //st.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        st.requestLayout();
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
        TextView st = snackbar.getView().findViewById(R.id.snackbar_text);
        st.setSingleLine(false);
        //st.setPadding(5,5,5,5);
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
        final AppCompatImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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

        final AppCompatImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void hideProgressView() {
        if (progressView != null) progressView.setVisibility(View.GONE);
        else NandX.print("progressView is NULL");
    }

    private void showProgressView() {
        if (progressView != null) progressView.setVisibility(View.VISIBLE);
        else NandX.print("progressView is NULL");
    }
}
