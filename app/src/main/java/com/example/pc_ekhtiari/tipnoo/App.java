package com.example.pc_ekhtiari.bottomnavigationview;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
     //   Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iransans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

