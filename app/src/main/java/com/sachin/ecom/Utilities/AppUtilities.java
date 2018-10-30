package com.sachin.ecom.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;


public class AppUtilities {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }

    public static String findError(VolleyError error) {
        if (error instanceof TimeoutError) {
            return "Timeout Error";
        } else if (error instanceof AuthFailureError) {
            return "Auth Failure Error";
        } else if (error instanceof ServerError) {
            return "Server Error";
        } else if (error instanceof NetworkError) {
            return "Network Error";
        } else if (error instanceof ParseError) {
            return "Parse Error";
        } else if (error instanceof NoConnectionError) {
            return "No Connection Error";
        } else {
            return "Other Error - " + error.getMessage();
        }
    }
}
