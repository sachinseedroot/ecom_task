package com.sachin.ecom.MVP;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.VolleyError;
import com.sachin.ecom.Volley.VolleyObject;
import com.sachin.ecom.Volley.VolleyResponseInterface;

import org.json.JSONArray;
import org.json.JSONObject;


public class Presenter implements MainMVP.presenter {

    private final MainMVP.view mainview;
    private ProgressDialog mProgressDialog;

    public Presenter(MainMVP.view view) {
        mainview = view;
    }


    @Override
    public void displayDialogError(VolleyError volleyError) {
        mainview.displayDialogError(volleyError);
    }

    @Override
    public void getdata() {
            VolleyObject.getSDKconfigInstance().fetchContentData(new VolleyResponseInterface() {
                @Override
                public void onResponse(JSONObject response, VolleyError volleyError) {
                    if (volleyError == null) {
                        mainview.getdata(response);
                    } else {
                        mainview.displayDialogError(volleyError);
                    }
                }
            });
    }
}
