package com.sachin.ecom.MVP;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;


public interface MainMVP {
    interface view{
        void displayDialogError(VolleyError volleyError);
        void getdata(JSONObject jsonArray);
    }

    interface presenter{
        void displayDialogError(VolleyError volleyError);
        void getdata();
    }
}
