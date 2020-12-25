package com.smnadim21.nadx.api;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

public interface GetResponse {
    void onResponse(
            int code,
            String msg,
            JSONObject object,
            Response response
    );

    void onErrorResponse(
            int code,
            String msg,
            JSONObject error,
            Response response
    );

    void onInternalError(
            int code,
            String msg,
            Response response
    );

    void onFailure(
            String msg,
            Throwable t
    );
}
