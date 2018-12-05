package com.example.hillavas.tipnoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.appnex.android.subscribe.AppnexPushSubscribeDialog;
import io.appnex.android.subscribe.ListenersCallback;

public class AppnexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appnex);

        new AppnexPushSubscribeDialog.Builder(this)
                .listeners(new ListenersCallback() {
                    @Override
                    public void LoadingFail() {

                    }

                    @Override
                    public void dialogDissmiss() {

                    }

                    @Override
                    public void purchaseDone() {

                    }

                    @Override
                    public void purchaseFail() {

                    }

                    @Override
                    public void networkFail() {

                    }
                })
                .show();

    }
}
