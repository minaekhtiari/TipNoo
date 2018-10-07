package com.example.hillavas.tipnoo.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//mport com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Adapters.TabViewPagerAdapter;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    final long DELAY_MS = 10;
    RecyclerView FavoriteRecycler;
    ViewPager Gallerypager;
    RecyclerView MostViewedRecycler;
    final long PERIOD_MS = 3000;
    RecyclerView SelectedRecycler;
    List<String> certificationText;
    int currentpage = 0;
    CircleIndicator indicator;
    ProgressDialog progressDialog;
    Timer timer;
    List<String> urls;

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
        urls = new ArrayList();
        certificationText = new ArrayList();
        Gallerypager = (ViewPager) rootview.findViewById(R.id.Gallerypager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), 0, false);
        SelectedRecycler = (RecyclerView) rootview.findViewById(R.id.selected_videos);
        MostViewedRecycler = (RecyclerView) rootview.findViewById(R.id.mostviewed_videos);
        FavoriteRecycler = (RecyclerView) rootview.findViewById(R.id.favorite_videos);
        SelectedRecycler.setLayoutManager(layoutManager);
        indicator = (CircleIndicator) rootview.findViewById(R.id.indicator);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void getGallery() {
        FileApi fileApi = RetroClient.getApiService();
    }
    public void getTabs() {

//        FileApi fileApi = RetroClient.getApiService();
//        retrofit2.Call<TabResults> tabResultsCall = fileApi.getPerfumeTabs(11);
//        tabResultsCall.enqueue(new Callback<TabResults>() {
//            @Override
//            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {
//                TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
//                for (int i = 0; i < response.body().getResult().size(); i++) {
//                    adapter.addFragment(new SubCategoryFragment(), response.body().getResult().get(i).getName());
//                }
//                firstViewPager.setAdapter(adapter);
////               tab1= response.body().getResult().get(1).getName();
////                response.body().getIsSuccessful();
////                setupViewPager(firstViewPager);
//            }
//
//
//            @Override
//            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {
//
//            }
//        });
    }


}
