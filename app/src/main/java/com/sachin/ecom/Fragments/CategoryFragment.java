package com.sachin.ecom.Fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Adapter.CategoryRecyclerAdapter;
import com.sachin.ecom.Controller.MainApplication;
import com.sachin.ecom.MVP.Presenter;
import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.AppUtilities;
import com.sachin.ecom.Utilities.TypeFaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class CategoryFragment extends Fragment {
    private Context mcontext;
    public static ProgressDialog mProgressDialog;
    private Presenter presenter;
    private RecyclerView recyclerViewList;
    private TextView textTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    public static CategoryFragment newInstance() {
        CategoryFragment f = new CategoryFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
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
        LocalBroadcastManager.getInstance(mcontext).registerReceiver(broadCastData,new IntentFilter("id_pass"));
        textTitle = (TextView) view.findViewById(R.id.titleTV);
        textTitle.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));

        recyclerViewList = (RecyclerView) view.findViewById(R.id.rec_list);
//        showProgress("Loading,Please wait...");

       ParseJsonDataAsync parseJsonDataAsync = new ParseJsonDataAsync();
       parseJsonDataAsync.execute();
    }



    public class ParseJsonDataAsync extends AsyncTask<Void, Void, ArrayList<CategoryDetails>> {

        @Override
        protected ArrayList<CategoryDetails> doInBackground(Void... voids) {
            ArrayList<CategoryDetails> categoryDetails = (ArrayList<CategoryDetails>) MainApplication.getDBinstance().myDAO().getAllCategories();
            ArrayList<ProductDetails> arrays = (ArrayList<ProductDetails>) MainApplication.getDBinstance().myDAO().getAllProducts();
           if(categoryDetails!=null){
               Collections.sort(categoryDetails, new Comparator<CategoryDetails>() {
                   @Override
                   public int compare(CategoryDetails s1, CategoryDetails s2) {
                       return s1.name.compareToIgnoreCase(s2.name);
                   }
               });
           }

            return categoryDetails;
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

    private BroadcastReceiver broadCastData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("id");
            ParseSubCategories parseSubCategories = new ParseSubCategories();
            parseSubCategories.execute(id);
        }
    };


    public class ParseSubCategories extends AsyncTask<String,Void,ArrayList<CategoryDetails>>{
        @Override
        protected ArrayList<CategoryDetails> doInBackground(String... strings) {
            String id= strings[0];
            ArrayList<CategoryDetails> categoryDetails = new ArrayList<>();
            if(!TextUtils.isEmpty(id)){
                String[] sepid = id.split(",");
                if(sepid!=null && sepid.length>0){

                    for(int i=0;i<sepid.length;i++){
                        String idcat = sepid[i];
                        idcat = idcat.replace(",","");
                        categoryDetails.add(MainApplication.getDBinstance().myDAO().getCategoryById(Integer.parseInt(idcat)));
                    }
                }
            }
            return categoryDetails;
        }

        @Override
        protected void onPostExecute(ArrayList<CategoryDetails> categoryDetails) {
            super.onPostExecute(categoryDetails);
            loadSubCategories(categoryDetails);
        }
    }

    private void loadSubCategories(ArrayList<CategoryDetails> jsonArray) {

        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(mcontext);
            builderSingle.setTitle("Select Sub-category");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.select_dialog_item);
            if (jsonArray != null && jsonArray.size() > 0) {

                for( int i=0;i<jsonArray.size();i++){
                    arrayAdapter.add(jsonArray.get(i).getName());
                }
            }

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
//                    ProductDetails opCode = AppUtilities.getOperatorObject(strName.toUpperCase(), appSkeleton, countryCode.toUpperCase());
//                    if (opCode != null) {
//                        resendOTP(countryCode, mobNos, opCode.operator_code);
//                    }
                }
            });
            if (!((MainActivity) mcontext).isFinishing()) {
                builderSingle.show();
            }
//            builderSingle.show();
        } catch (Exception e) {
          e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            if(broadCastData!=null) {
                mcontext.unregisterReceiver(broadCastData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
