package com.smnadim21.nandroidx.app;

import android.content.Context;

public class NandroidX {
    private static Context context;

    NandroidX(Context ctx) {
        context = ctx;
    }

    public static Context getContext() {
        return context;
    }
}
