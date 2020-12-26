package com.smnadim21.nadx.activity;

import android.os.Bundle;
import android.view.View;


import com.smnadim21.nadx.tools.NandX;
import com.smnadim21.nadx.tools.network.InternetCheck;

public class InternetActivity extends BaseActivity implements InternetCheck.Consumer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InternetCheck(InternetActivity.this);
    }

    @Override
    public void accept(Boolean internet) {
        if (!internet) {
            NandX.snackErr(
                    getBaseActivity(),
                    "NO INTERNET",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }
            );
        }
    }
}
