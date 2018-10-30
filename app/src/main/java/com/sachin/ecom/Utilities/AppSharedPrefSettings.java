package com.sachin.ecom.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPrefSettings {

    public static void setRawResponse(Context context, String RawResponse) {
        SharedPreferences prefs = Prefs.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("RawResponse", RawResponse);
        editor.commit();
    }

    public static String getRawResponse(Context context) {
        SharedPreferences prefs = Prefs.get(context);
        return prefs.getString("RawResponse", "");
    }



}
