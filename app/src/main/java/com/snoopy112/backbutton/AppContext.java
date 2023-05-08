package com.snoopy112.backbutton;

import android.app.Application;
import android.content.Context;


public class AppContext extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}