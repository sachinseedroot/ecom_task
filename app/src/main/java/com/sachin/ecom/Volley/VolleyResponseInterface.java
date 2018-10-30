package com.sachin.ecom.Volley;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface VolleyResponseInterface {

    void onResponse(JSONObject response, VolleyError volleyError);

}
