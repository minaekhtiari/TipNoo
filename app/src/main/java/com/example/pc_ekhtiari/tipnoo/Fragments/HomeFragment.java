package com.example.pc_ekhtiari.tipnoo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pc_ekhtiari.bottomnavigationview.R;
import com.example.pc_ekhtiari.tipnoo.Adapters.TabViewPagerAdapter;
import com.example.pc_ekhtiari.tipnoo.Models.TabResults;
import com.example.pc_ekhtiari.tipnoo.Retrofit.FileApi;
import com.example.pc_ekhtiari.tipnoo.Retrofit.RetroClient;

import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager firstViewPager;
    String tab1, tab2, tab3;

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        firstViewPager = (ViewPager) rootview.findViewById(R.id.pager);
        getTabs();
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setupViewPager(ViewPager viewPager) {


//        adapter.addFragment(new TrimmFragment(), tab1);
//         adapter.addFragment(new TrimmFragment(), tab2);
//         adapter.addFragment(new TrimmFragment(), tab3);
//        adapter.addFragment(new Tab1Fragment(), "Tab 4");

    }

    public void getTabs() {

        FileApi fileApi = RetroClient.getApiService();
        retrofit2.Call<TabResults> tabResultsCall = fileApi.getPerfumeTabs(11);
        tabResultsCall.enqueue(new Callback<TabResults>() {
            @Override
            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {
                TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
                for (int i = 0; i < response.body().getResult().size(); i++) {
                    adapter.addFragment(new SubCategoryFragment(), response.body().getResult().get(i).getName());
                }
                firstViewPager.setAdapter(adapter);
//               tab1= response.body().getResult().get(1).getName();
//                response.body().getIsSuccessful();
//                setupViewPager(firstViewPager);
            }


            @Override
            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {

            }
        });
    }


}
