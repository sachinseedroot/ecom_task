package com.sachin.ecom.Controller;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.DB.AppDataBase;
import com.sachin.ecom.Volley.VolleyObject;
import com.sachin.ecom.Volley.VolleyObject;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {


    public static Context mcontext;
    public static AppDataBase appDataBase;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mcontext = this;
        VolleyObject.initialize(mcontext);
        getDBinstance();
    }


    public static AppDataBase getDBinstance() {

        try {
            if (appDataBase == null) {
                appDataBase = Room.databaseBuilder(mcontext,AppDataBase.class,"headecomdb").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appDataBase;
    }
}
