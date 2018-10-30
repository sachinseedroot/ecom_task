package com.sachin.ecom.Controller;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Volley.VolleyObject;
import com.sachin.ecom.Volley.VolleyObject;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {


    public static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mcontext = this;
        VolleyObject.initialize(mcontext);
    }



}
