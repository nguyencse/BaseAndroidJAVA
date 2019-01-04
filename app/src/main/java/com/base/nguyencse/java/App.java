package com.base.nguyencse.java;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.google.gson.Gson;

public class App extends MultiDexApplication {
    private static final String TAG = App.class.getSimpleName();
    private static App instance = null;
    private static Gson mGSon;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static Gson getGSon() {
        if (mGSon == null) {
            mGSon = new Gson();
        }
        return mGSon;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}