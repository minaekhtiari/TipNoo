package com.example.hillavas.tipnoo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//
//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Adapters.TabViewPagerAdapter;
import com.example.hillavas.tipnoo.Models.TabResults;

import com.example.hillavas.tipnoo.MoreVideoListActivity;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CategoryFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager tabsViewPager;
    int parentid;
    ArrayList cateid;


    TextView lblFailMessage;
    AppCompatButton btnFailTryAgain;
    RelativeLayout relativeLayoutFail, relativeLayoutLoading;

    public CategoryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_category, container, false);

        tabsViewPager = (ViewPager) rootview.findViewById(R.id.pager);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);


        relativeLayoutFail = (RelativeLayout) rootview.findViewById(R.id.layout_fail);
        relativeLayoutLoading = (RelativeLayout) rootview.findViewById(R.id.layout_loading);
        lblFailMessage = rootview.findViewById(R.id.lbl_fail);
        btnFailTryAgain = rootview.findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);


        getTabs();


        return rootview;

    }


    public void getTabs() {

        loadingOrFail(true, true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            parentid = bundle.getInt("arg");

        }

        FileApi fileApi = RetroClient.getApiService();
        final retrofit2.Call<TabResults> tabResultsCall = fileApi.getAlleContent(parentid);
        tabResultsCall.enqueue(new Callback<TabResults>() {
            @Override
            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {
                TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());

                if (response.isSuccessful()) {
                    if (response.body().getIsSuccessful()) {
                        for (int i = 0; i < response.body().getResult().size(); i++) {
                            SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                            adapter.addFragment(subCategoryFragment, response.body().getResult().get(i).getName());
                            //cateid.set(i, response.body().getResult().get(i).getCategoryId());
                            int categoryid = response.body().getResult().get(i).getCategoryId();
                            Bundle bundle = new Bundle();
                            // bundle.putParcelableArrayList("argContent",cateid);
                            bundle.putInt("argContent", categoryid);
                            subCategoryFragment.setArguments(bundle);
                        }

                        tabsViewPager.setAdapter(adapter);
//                for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                    //noinspection ConstantConditions
//                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
//
//                    tabLayout.getTabAt(i).setCustomView(tv);
//                }
                        tabLayout.setupWithViewPager(tabsViewPager);
                        tabsViewPager.setVisibility(View.VISIBLE);

                        loadingOrFail(true, false);
                    } else {
                        loadingOrFail(false, true);//fail layout visible
                        lblFailMessage.setText(String.valueOf(response.body().getMessage()));
                    }
                } else {
                    loadingOrFail(false, true);//fail layout visible
                    lblFailMessage.setText(getString(R.string.serverError));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {

                loadingOrFail(false, true);//fail layout visible
                lblFailMessage.setText(getString(R.string.noConnection));

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fail_try_again:
                getTabs();
                break;

        }

    }

    void loadingOrFail(boolean hasLoading, boolean showing) {
        if (hasLoading)//if is loading layout
        {
            relativeLayoutFail.setVisibility(View.GONE);
            //layout fail hidden gone
            if (showing) {
                relativeLayoutLoading.setVisibility(View.VISIBLE);
                //layout laoding visible

            } else {
                relativeLayoutLoading.setVisibility(View.GONE);
                //layout loading hidden
            }
        } else {// if is fail layout

            relativeLayoutLoading.setVisibility(View.GONE);
            //layout loadign hidden
            if (showing) {
                relativeLayoutFail.setVisibility(View.VISIBLE);

                //layout fail visible
            } else {
                relativeLayoutFail.setVisibility(View.GONE);
                //layout fail hidden
            }
        }
    }

}


