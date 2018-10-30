package com.sachin.ecom.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.MVP.Presenter;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.AppUtilities;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener {
    private Context mcontext;
    public static ProgressDialog mProgressDialog;
    private Presenter presenter;
    private ListView recyclerViewList;
    private TextView viewtype;
    private TextView textTitle;
    private String detailItemModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    public static ListFragment newInstance(String d_id) {
        ListFragment f = new ListFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("data", d_id);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        detailItemModel = getArguments().getString("data","");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initialize(view);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void initialize(View view) throws JSONException {
//        initProgressbar();
        viewtype = (TextView)view.findViewById(R.id.viewtype);
        textTitle = (TextView) view.findViewById(R.id.titleTV);
        textTitle.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        viewtype.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        viewtype.setOnClickListener(this);

        recyclerViewList = (ListView) view.findViewById(R.id.rec_list);
//        showProgress("Loading,Please wait...");
        if(!TextUtils.isEmpty(detailItemModel)) {
            JSONArray jsonArray = new JSONArray(detailItemModel);
            ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync();
            parseJsonDataAsync.execute(jsonArray);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.viewtype:
                if(viewtype.getText().toString().toLowerCase().equalsIgnoreCase("mapview")){
                    recyclerViewList.setVisibility(View.GONE);
                    viewtype.setText("ListView");
                }else{
                    recyclerViewList.setVisibility(View.VISIBLE);
                    viewtype.setText("MapView");
                }
                break;
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
                        ProductDetails productItemDetail = new ProductDetails(jsonObject);
                        productItemDetailsList.add(productItemDetail);
                    }
                }
                if (productItemDetailsList != null && productItemDetailsList.size() > 0) {
                    return productItemDetailsList;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<ProductDetails> productItemDetails) {
            super.onPostExecute(productItemDetails);
            if (productItemDetails != null && productItemDetails.size() > 0) {
                recyclerViewList.setVisibility(View.VISIBLE);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
//                ListBaseAdapter listRecyclerAdapter = new ListBaseAdapter(mcontext, productItemDetails);
//                recyclerViewList.setLayoutManager(linearLayoutManager);
//                recyclerViewList.setAdapter(listRecyclerAdapter);

            } else {
                recyclerViewList.setVisibility(View.GONE);
                AppUtilities.showAlertDialog(mcontext, "Oops!", "Something went wrong...");
            }
        }
    }

}
