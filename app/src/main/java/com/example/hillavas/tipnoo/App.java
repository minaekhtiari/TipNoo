package com.example.hillavas.tipnoo;

import android.app.Application;
import android.content.Context;



import com.crashlytics.android.Crashlytics;


import io.appnex.android.notification.Appnex;
import io.fabric.sdk.android.Fabric;
import net.jhoobin.jhub.CharkhoneSdkApp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
     //   Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
      Appnex.init(this);

      CharkhoneSdkApp.initSdk((Context) this, getSecrets(), false);
//
//        JsonUtils.loadConfigInfo(this).getBatchId();
//
//
//        Batch.setConfig(new Config(JsonUtils.loadConfigInfo(this).getBatchId()));
//
//        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public String[] getSecrets() {
        return getResources().getStringArray(R.array.secrets);
    }
}

