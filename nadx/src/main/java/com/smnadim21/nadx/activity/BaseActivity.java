package com.smnadim21.nadx.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


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
}