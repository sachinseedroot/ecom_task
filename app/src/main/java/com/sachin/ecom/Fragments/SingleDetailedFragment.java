package com.sachin.ecom.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Utilities.TypeFaceHelper;


public class SingleDetailedFragment extends Fragment {
    private Context mcontext;
    public static ProgressDialog mProgressDialog;
    private ProductDetails detailItemModel;
    private TextView textTitle;
    private TextView descTV;
    private TextView backTV;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    public static SingleDetailedFragment newInstance(ProductDetails d_id) {
        SingleDetailedFragment f = new SingleDetailedFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("id", d_id);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        detailItemModel = (ProductDetails) getArguments().getParcelable("id");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
//            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
            initialize(view);


        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void initialize(View view) {


        textTitle = (TextView) view.findViewById(R.id.titleTV);
        descTV = (TextView) view.findViewById(R.id.descTV);
        textTitle.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansBold));
        descTV.setTypeface(TypeFaceHelper.getInstance(mcontext).getStyleTypeFace(TypeFaceHelper.FiraSansLight));
        descTV.setTextColor(ContextCompat.getColor(mcontext, R.color.colorPrimaryDark));

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
}
