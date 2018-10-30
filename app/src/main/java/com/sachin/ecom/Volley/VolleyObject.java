package com.sachin.ecom.Volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Utilities.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyObject {

    private static Context mcontext;
    private static MyVolleySingleton myVolleySingleton;
    private static VolleyObject volleyObject;


    public static void initialize(Context context) {
        mcontext = context.getApplicationContext();
        myVolleySingleton = new MyVolleySingleton(mcontext);
    }

    public void fetchContentData(final VolleyResponseInterface volleyResponseInterface) {

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, AppConstants.URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyResponseInterface.onResponse(response, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseInterface.onResponse(null, error);
            }
        });

        //Retry Policy
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        myVolleySingleton.addToRequestQueue(jsonArrayRequest);
    }

    public static VolleyObject getSDKconfigInstance() {

        try {
            if (volleyObject == null) {
                volleyObject = new VolleyObject();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

        return volleyObject;
    }
}
