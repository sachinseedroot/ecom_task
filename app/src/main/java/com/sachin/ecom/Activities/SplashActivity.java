package com.sachin.ecom.Activities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Adapter.CategoryRecyclerAdapter;
import com.sachin.ecom.Controller.MainApplication;
import com.sachin.ecom.DB.AppDataBase;
import com.sachin.ecom.MVP.MainMVP;
import com.sachin.ecom.MVP.Presenter;
import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.AppUtilities;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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
        refreshTV.setVisibility(View.VISIBLE);
        AppUtilities.showAlertDialog(mcontext, AppUtilities.findError(volleyError).toUpperCase(), "Please check your internet connection & try again...");
    }

    @Override
    public void getdata(JSONObject jsonArray) {


        JSONArray jsonArrays = jsonArray.optJSONArray("categories");
        ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync();
        parseJsonDataAsync.execute(jsonArrays);

    }

    public class ParseJsonDataAsync extends AsyncTask<JSONArray, Void, ArrayList<CategoryDetails>> {

        @Override
        protected ArrayList<CategoryDetails> doInBackground(JSONArray... jsonArrays) {
            ArrayList<CategoryDetails> categoryDetails = new ArrayList<>();
            if (jsonArrays != null && jsonArrays[0] != null && jsonArrays[0].length() > 0) {

                for (int i = 0; i < jsonArrays[0].length(); i++) {
                    JSONObject jsonObject = jsonArrays[0].optJSONObject(i);
                    if (jsonObject != null) {
                        CategoryDetails productItemDetail = new CategoryDetails();
                        productItemDetail.setId(jsonObject.optInt("id"));
                        productItemDetail.setName(jsonObject.optString("name").trim());
                        JSONArray jsonArray = jsonObject.optJSONArray("child_categories");
                        JSONArray products_list = jsonObject.optJSONArray("products");
                        loadProductList(productItemDetail.getName(),products_list);
                        if (jsonArray != null && jsonArray.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int k = 0; k < jsonArray.length(); k++) {
                                int ids = jsonArray.optInt(k);
                                sb.append(Integer.toString(ids));
                                sb.append(",");
                            }
                            productItemDetail.setChildCategories(sb.toString());
                        }
                        if(productItemDetail!=null) {
                            MainApplication.getDBinstance().myDAO().addCategories(productItemDetail);
                            categoryDetails.add(productItemDetail);
                        }
                    }
                }
            }
            return categoryDetails;
        }

        @Override
        protected void onPostExecute(final ArrayList<CategoryDetails> productItemDetails) {
            super.onPostExecute(productItemDetails);

            if(productItemDetails!=null) {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter_from_right, R.anim.hold);
                finish();
            }else{
                Toast.makeText(mcontext,"Something went wrong...",Toast.LENGTH_SHORT).show();
            }
        }
    }


   public ProductDetails loadProductList(String categoryname,JSONArray jsonArray){
        if(jsonArray!=null){
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                if (jsonObject != null) {
                    ProductDetails productDetails = new ProductDetails();
                    productDetails.setP_id(jsonObject.optInt("id"));
                    productDetails.setP_name(jsonObject.optString("name"));
                    productDetails.setCategory_name(categoryname);
                    JSONArray variants = jsonObject.optJSONArray("variants");
                    if(variants!=null && variants.length()>0){
                        for(int k=0;k<variants.length();k++){
                            JSONObject varObject = variants.optJSONObject(k);
                            if(varObject!=null){
                                productDetails.setV_id(varObject.optInt("id"));
                                productDetails.setV_color(varObject.optString("color"));
                                productDetails.setV_size(varObject.optInt("size"));
                                productDetails.setV_price(varObject.optInt("price"));
                                System.out.println("----"+categoryname+" - "+productDetails.getP_name()+"- v"+productDetails.getV_id()+" "+productDetails.getV_color());
                                MainApplication.getDBinstance().myDAO().addProducts(productDetails);
                            }
                        }
                    }else{
                        MainApplication.getDBinstance().myDAO().addProducts(productDetails);
                    }
                    return productDetails;
                }
            }
        }
        return null;
   }
}
