package com.smnadim21.nadx.api;

import org.json.JSONObject;

import retrofit2.Response;

public interface GetResponse {
    void onResponse(
            int code,
            String msg,
            JSONObject object,
            Response<String> response
    );

    void onErrorResponse(
            int code,
            String msg,
            JSONObject error,
            Response<String> response
    );

    void onInternalError(
            int code,
            String msg,
            Response<String> response
    );

    void onFailure(
            String msg,
            Throwable t
    );
}
