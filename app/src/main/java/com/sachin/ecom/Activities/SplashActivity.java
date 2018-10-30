package com.sachin.ecom.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.MVP.MainMVP;
import com.sachin.ecom.MVP.Presenter;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.AppUtilities;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public class SplashActivity extends AppCompatActivity implements MainMVP.view {

    private static int SPLASH_TIME_OUT = 300;
    private ProgressBar progressBar_splash;
    private Context mcontext;
    private TextView refreshTV;
    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mcontext = this;

        progressBar_splash = (ProgressBar) findViewById(R.id.progressBar_splash);
        refreshTV = (TextView) findViewById(R.id.refreshTV);
        refreshTV.setText(mcontext.getResources().getString(R.string.fa_refresh) + " Retry");
        refreshTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        refreshTV.setVisibility(View.GONE);
        progressBar_splash.setVisibility(View.VISIBLE);
        presenter = new Presenter(this);
        presenter.getdata();

        refreshTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_splash.setVisibility(View.VISIBLE);
                refreshTV.setVisibility(View.GONE);
                presenter.getdata();
            }
        });

    }

    @Override
    public void displayDialogError(VolleyError volleyError) {
        progressBar_splash.setVisibility(View.GONE);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray((String) ProductDetails.readObject(mcontext, "offlinedata"));
        } catch (Exception e) {
            Crashlytics.logException(e);
            jsonArray = null;
        }
        if (jsonArray == null) {
            refreshTV.setVisibility(View.VISIBLE);
            AppUtilities.showAlertDialog(mcontext, AppUtilities.findError(volleyError).toUpperCase(), "Please check your internet connection & try again...");
        } else {
            Toast.makeText(mcontext, AppUtilities.findError(volleyError) + ": loading offline data", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putExtra("data", jsonArray.toString());
            startActivity(i);
            overridePendingTransition(R.anim.enter_from_right, R.anim.hold);
            finish();

        }
    }

    @Override
    public void getdata(JSONObject jsonArray) {

//        AppSharedPrefSettings.setRawResponse(mcontext, jsonArray.toString());
        try {
            ProductDetails.writeObject(mcontext, "offlinedata", jsonArray.toString());
        } catch (IOException e) {
            Crashlytics.logException(e);
        }
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        i.putExtra("data", jsonArray.toString());
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.hold);
        finish();


    }
}
