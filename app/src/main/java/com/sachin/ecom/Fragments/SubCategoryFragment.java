package com.sachin.ecom.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Adapter.CategoryRecyclerAdapter;
import com.sachin.ecom.MVP.Presenter;
import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.AppUtilities;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SubCategoryFragment extends Fragment {
    private Context mcontext;
    public static ProgressDialog mProgressDialog;
    private Presenter presenter;
    private RecyclerView recyclerViewList;
    private TextView textTitle;
    private String detailItemModel;
    private TextView backTV;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    public static SubCategoryFragment newInstance(String d_id) {
        SubCategoryFragment f = new SubCategoryFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("data", d_id);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_sub, container, false);
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

        textTitle = (TextView) view.findViewById(R.id.titleTV);
        textTitle.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));

        recyclerViewList = (RecyclerView) view.findViewById(R.id.rec_list);
//        showProgress("Loading,Please wait...");
        backTV = (TextView) view.findViewById(R.id.backTV);
        backTV.setText(mcontext.getResources().getString(R.string.fa_back));
        backTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FONT_AWESOME));
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mcontext).onBackPressed();
            }
        });

        if(!TextUtils.isEmpty(detailItemModel)) {

            JSONArray jsonArray = new JSONArray(detailItemModel);
            ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync();
            parseJsonDataAsync.execute(jsonArray);
        }
    }



    public class ParseJsonDataAsync extends AsyncTask<JSONArray, Void, ArrayList<CategoryDetails>> {

        @Override
        protected ArrayList<CategoryDetails> doInBackground(JSONArray... jsonArrays) {
            if (jsonArrays != null && jsonArrays[0]!=null && jsonArrays[0].length() > 0) {
                ArrayList<CategoryDetails> productItemDetailsList = new ArrayList<>();
                for (int i = 0; i < jsonArrays[0].length(); i++) {
                    JSONObject jsonObject = jsonArrays[0].optJSONObject(i);
                    if (jsonObject != null) {
                        CategoryDetails productItemDetail = new CategoryDetails();
                        productItemDetailsList.add(productItemDetail);
                    }
                }
                if (productItemDetailsList != null && productItemDetailsList.size() > 0) {
                    Collections.sort(productItemDetailsList, new Comparator<CategoryDetails>() {
                        @Override
                        public int compare(CategoryDetails s1, CategoryDetails s2) {
                            return s1.name.compareToIgnoreCase(s2.name);
                        }
                    });

                    return productItemDetailsList;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<CategoryDetails> productItemDetails) {
            super.onPostExecute(productItemDetails);
            if (productItemDetails != null && productItemDetails.size() > 0) {
                recyclerViewList.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
                CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(mcontext, productItemDetails);
                recyclerViewList.setLayoutManager(linearLayoutManager);
                recyclerViewList.setAdapter(categoryRecyclerAdapter);

            } else {
                recyclerViewList.setVisibility(View.GONE);
                AppUtilities.showAlertDialog(mcontext, "Oops!", "No product found!!!");
            }
        }
    }

}
