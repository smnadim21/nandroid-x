package com.smnadim21.nadx.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;
import com.smnadim21.nadx.R;


public class BaseActivity extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        //setTheme(R.style.AppThemeNoActionBar);
        activity = BaseActivity.this;
    }

    public Activity getBaseActivity() {
        return activity;
    }

    public void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager)
                getBaseActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public void snackErr(final String mainTextString,
                         View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                getBaseActivity().findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("Close", listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red_900));
        snackbar.show();
    }

    public void snackOK(
            final String mainTextStringId,
            View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                getBaseActivity().findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getBaseActivity(), R.color.green_900));
        snackbar.show();
    }

    public void snackInfo(
            final String mainTextStringId) {
        Snackbar snackbar = Snackbar.make(
                getBaseActivity().findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getBaseActivity(), R.color.light_blue_900));
        snackbar.show();
    }
}