package com.sachin.ecom.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Fragments.SingleDetailedFragment;
import com.sachin.ecom.Model.ProductDetails;
import com.sachin.ecom.R;
import com.sachin.ecom.Fragments.ListFragment;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private ListFragment listFragment;
    private FrameLayout frameLayoutContainer;
    private Stack<Fragment> fragmentStack;
    private SingleDetailedFragment singleDetailFragment;
    private boolean doubleBackToExitPressedOnce = false;
    private Context mcontext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcontext = this;
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("data")) {
            String data = intent.getStringExtra("data");
            initialize(data);
        }else{
            Toast.makeText(mcontext,"something went wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void initialize(String data) {
        frameLayoutContainer = (FrameLayout) findViewById(R.id.container);
        fragmentStack = new Stack<Fragment>();
        loadListFragment(data);
    }

    private void loadListFragment(String data) {
        try {
            if (listFragment == null) {
                listFragment = ListFragment.newInstance(data);
            }
            if (listFragment.isAdded()) {
                return;
            }
            fragmentStack.clear();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.hold);
            ft.add(frameLayoutContainer.getId(), listFragment);
            if (fragmentStack.size() > 0) {
                fragmentStack.lastElement().onPause();
                ft.hide(fragmentStack.lastElement());
            }
            fragmentStack.push(listFragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public void loadSingleDetailFragment(ProductDetails id) {
        try {

            singleDetailFragment = SingleDetailedFragment.newInstance(id);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.hold);
            ft.add(frameLayoutContainer.getId(), singleDetailFragment);
            if (fragmentStack.size() > 0) {
                fragmentStack.lastElement().onPause();
                ft.hide(fragmentStack.lastElement());
            }
            fragmentStack.push(singleDetailFragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            super.onBackPressed();
            return;
        }


        if (fragmentStack.size() > 1) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = fragmentStack.pop();


            ft.setCustomAnimations(R.anim.hold, R.anim.exit_to_right);

            Fragment lastFragment = fragmentStack.lastElement();

            lastFragment.onPause();
            ft.remove(fragment);
            lastFragment.onResume();
            ft.show(lastFragment);
            ft.commit();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(mcontext, "Click again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
