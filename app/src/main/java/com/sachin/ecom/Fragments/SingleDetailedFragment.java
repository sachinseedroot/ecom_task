package com.sachin.ecom.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Adapter.ProductListRecyclerAdapter;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SingleDetailedFragment extends Fragment {
    private Context mcontext;
    public static ProgressDialog mProgressDialog;
    private ArrayList<ProductDetails> detailItemModelArray;
    private TextView textTitle;
    private TextView backTV;
    private RecyclerView rec_list_product;
    private String response;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    public static SingleDetailedFragment newInstance(String d_id) {
        SingleDetailedFragment f = new SingleDetailedFragment();
        Bundle args = new Bundle();
        args.putString("id", d_id);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        response =  getArguments().getString("id");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            initialize(view);
    }

    private void initialize(View view) {


        textTitle = (TextView) view.findViewById(R.id.titleTV);
        textTitle.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        rec_list_product = (RecyclerView) view.findViewById(R.id.rec_list_product);

        backTV = (TextView) view.findViewById(R.id.backTV);
        backTV.setText(mcontext.getResources().getString(R.string.fa_back));
        backTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mcontext).onBackPressed();
            }
        });
        initProgressbar();

        ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync();
        try {
            parseJsonDataAsync.execute(new JSONArray(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initProgressbar() {
        mProgressDialog = new ProgressDialog(mcontext);
        //  mProgressDialog.setMessage("Loading, please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(true);
    }

    public static void showProgress(String msg) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(msg);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public static void stopProgress() {

        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public class ParseJsonDataAsync extends AsyncTask<JSONArray, Void, ArrayList<ProductDetails>> {

        @Override
        protected ArrayList<ProductDetails> doInBackground(JSONArray... jsonArrays) {
            if (jsonArrays != null && jsonArrays[0]!=null && jsonArrays[0].length() > 0) {
                ArrayList<ProductDetails> productItemDetailsList = new ArrayList<>();
                for (int i = 0; i < jsonArrays[0].length(); i++) {
                    JSONObject jsonObject = jsonArrays[0].optJSONObject(i);
                    if (jsonObject != null) {
                        ProductDetails productItemDetail = new ProductDetails();
                        productItemDetailsList.add(productItemDetail);
                    }
                }
                if (productItemDetailsList != null && productItemDetailsList.size() > 0) {
                    Collections.sort(productItemDetailsList, new Comparator<ProductDetails>() {
                        @Override
                        public int compare(ProductDetails s1, ProductDetails s2) {
                            return s1.p_name.compareToIgnoreCase(s2.p_name);
                        }
                    });

                    return productItemDetailsList;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<ProductDetails> productItemDetails) {
            super.onPostExecute(productItemDetails);
            if (productItemDetails != null && productItemDetails.size() > 0) {
                rec_list_product.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
                ProductListRecyclerAdapter listRecyclerAdapter = new ProductListRecyclerAdapter(mcontext, productItemDetails);
                rec_list_product.setLayoutManager(linearLayoutManager);
                rec_list_product.setAdapter(listRecyclerAdapter);

            } else {
                rec_list_product.setVisibility(View.GONE);
                showAlertDialog(mcontext, "Oops!", "No product found!!!");
            }
        }
    }

    public void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        ((MainActivity)mcontext).onBackPressed();
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
}
