package com.example.pc_ekhtiari.tipnoo.Fragments;

import android.content.Context;
import android.net.Uri;
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

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager tabsViewPager;
    int parentid;
    ArrayList cateid;
    public CategoryFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTabs();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview= inflater.inflate(R.layout.fragment_category, container, false);
        // textView=rootview.findViewById(R.id.perfume);
        tabsViewPager = (ViewPager) rootview.findViewById(R.id.pager);
        getTabs();
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabsViewPager);



//        perfumViewPager =  rootview.findViewById(R.id.pager);
//
//        tabLayout = rootview.findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(perfumViewPager);

        return rootview;

    }


    public void getTabs() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            parentid = bundle.getInt("arg");

        }
        FileApi fileApi = RetroClient.getApiService();
        retrofit2.Call<TabResults> tabResultsCall = fileApi.getPerfumeTabs(parentid);
        tabResultsCall.enqueue(new Callback<TabResults>() {
            @Override
            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {
                TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
                for (int i = 0; i < response.body().getResult().size(); i++) {
                    SubCategoryFragment subCategoryFragment=new SubCategoryFragment();
                    adapter.addFragment(subCategoryFragment, response.body().getResult().get(i).getName());
                    //cateid.set(i, response.body().getResult().get(i).getCategoryId());
                    int categoryid=response.body().getResult().get(i).getCategoryId();
                    Bundle bundle = new Bundle();
                    // bundle.putParcelableArrayList("argContent",cateid);
                    bundle.putInt("argContent",categoryid);
                    subCategoryFragment.setArguments(bundle);
                }
                tabsViewPager.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {

            }
        });
    }
}
