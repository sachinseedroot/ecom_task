package com.sachin.ecom.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.sachin.ecom.Fragments.SingleDetailedFragment;
import com.sachin.ecom.Fragments.SubCategoryFragment;
import com.sachin.ecom.R;
import com.sachin.ecom.Fragments.CategoryFragment;

import org.json.JSONArray;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private CategoryFragment listFragment;
    private FrameLayout frameLayoutContainer;
    private Stack<Fragment> fragmentStack;
    private SingleDetailedFragment singleDetailFragment;
    private boolean doubleBackToExitPressedOnce = false;
    private Context mcontext;
    private SubCategoryFragment listsubFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcontext = this;
        initialize();
    }

    private void initialize() {
        frameLayoutContainer = (FrameLayout) findViewById(R.id.container);
        fragmentStack = new Stack<Fragment>();

        loadListFragment();
    }

    private void loadListFragment() {
        try {
            if (listFragment == null) {
                listFragment = CategoryFragment.newInstance();
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

    public void loadSubListFragment(String data) {
        try {
            if (listsubFragment == null) {
                listsubFragment = SubCategoryFragment.newInstance(data);
            }
            if (listsubFragment.isAdded()) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.hold);
            ft.add(frameLayoutContainer.getId(), listsubFragment);
            if (fragmentStack.size() > 0) {
                fragmentStack.lastElement().onPause();
                ft.hide(fragmentStack.lastElement());
            }
            fragmentStack.push(listsubFragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public void loadSingleDetailFragment(String ids) {
        try {

            singleDetailFragment = SingleDetailedFragment.newInstance(ids);

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

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }

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
