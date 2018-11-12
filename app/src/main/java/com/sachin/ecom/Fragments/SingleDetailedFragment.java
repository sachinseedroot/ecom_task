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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Adapter.ProductListRecyclerAdapter;
import com.sachin.ecom.Controller.MainApplication;
import com.sachin.ecom.Model.CategoryDetails;
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
    private String ids;
    private TextView moTV;
    private TextView msTV;
    private TextView mvTV;
    private TextView allTV;
    private ArrayList<ProductDetails> productItemDetailsMain=new ArrayList<>();

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
        ids = getArguments().getString("id");
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

        allTV = (TextView) view.findViewById(R.id.allTV);
        allTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        mvTV = (TextView) view.findViewById(R.id.mvTV);
        mvTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        msTV = (TextView) view.findViewById(R.id.msTV);
        msTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        moTV = (TextView) view.findViewById(R.id.moTV);
        moTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));

        rec_list_product = (RecyclerView) view.findViewById(R.id.rec_list_product);

        backTV = (TextView) view.findViewById(R.id.backTV);
        backTV.setText(mcontext.getResources().getString(R.string.fa_back));
        backTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mcontext).onBackPressed();
            }
        });
        initProgressbar();

        ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync(0);
        parseJsonDataAsync.execute(ids);

        allTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
        allTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white));

        msTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                allTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));


                msTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
                msTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white));

                mvTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                mvTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                moTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                moTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync(3);
                parseJsonDataAsync.execute(ids);
            }
        });

        mvTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
                mvTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white));

                msTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                msTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                moTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                moTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                allTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                allTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync(1);
                parseJsonDataAsync.execute(ids);
            }
        });

        moTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
                moTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white));

                msTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                msTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                mvTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                mvTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                allTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                allTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync(2);
                parseJsonDataAsync.execute(ids);
            }
        });

        allTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));
                allTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white));

                msTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                msTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                mvTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                mvTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                moTV.setTextColor(ContextCompat.getColor(mcontext, R.color.white));
                moTV.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorPrimary));

                ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync(0);
                parseJsonDataAsync.execute(ids);
            }
        });
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

    public class ParseJsonDataAsync extends AsyncTask<String, Void, ArrayList<ProductDetails>> {
        int mType;

        ParseJsonDataAsync(int type) {
            mType = type;
        }

        @Override
        protected ArrayList<ProductDetails> doInBackground(String... strings) {
            String id = strings[0];
            ArrayList<ProductDetails> productDetails = new ArrayList<>();
            if (!TextUtils.isEmpty(id)) {
                String[] sepid = id.split(",");
                if (sepid != null && sepid.length > 0) {

                    for (int i = 0; i < sepid.length; i++) {
                        String idcat = sepid[i];
                        idcat = idcat.replace(",", "");
                        if(mType==0) {
                            productDetails.addAll(MainApplication.getDBinstance().myDAO().getProductsList(Integer.parseInt(idcat)));
                        }else if(mType==1) {
                            productDetails.addAll(MainApplication.getDBinstance().myDAO().getProductsListViewed(Integer.parseInt(idcat)));
                        }else if(mType==2) {
                            productDetails.addAll(MainApplication.getDBinstance().myDAO().getProductsListOrdered(Integer.parseInt(idcat)));
                        }else if(mType==3) {
                            productDetails.addAll(MainApplication.getDBinstance().myDAO().getProductsListShared(Integer.parseInt(idcat)));
                        }
                    }
                } else {
                    productDetails.addAll(MainApplication.getDBinstance().myDAO().getProductsList(Integer.parseInt(id)));
                }
            }
            return productDetails;
        }

        @Override
        protected void onPostExecute(final ArrayList<ProductDetails> productItemDetails) {
            super.onPostExecute(productItemDetails);
           loadList(productItemDetails,mType);
        }
    }

    public void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
//                        ((MainActivity) mcontext).onBackPressed();
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }

    public void loadList(ArrayList<ProductDetails> productItemDetails,int mType){
        if (productItemDetails != null && productItemDetails.size() > 0) {

            rec_list_product.removeAllViews();
            rec_list_product.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
            productItemDetailsMain = productItemDetails;
            ProductListRecyclerAdapter listRecyclerAdapter = new ProductListRecyclerAdapter(mcontext, productItemDetailsMain, mType);
            rec_list_product.setLayoutManager(linearLayoutManager);
            rec_list_product.setAdapter(listRecyclerAdapter);

        } else {
            rec_list_product.setVisibility(View.GONE);
            showAlertDialog(mcontext, "Oops!", "No product found!!!");
        }
    }
}
