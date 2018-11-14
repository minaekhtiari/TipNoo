package com.example.hillavas.tipnoo.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


//mport com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Adapters.HomeContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.HomeViewPagerAdapter;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.MoreVideoListActivity;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {
    final long DELAY_MS = 10;
    RecyclerView favoriteRecycler;
    ViewPager galleryPager;
    RecyclerView mostViewedRecycler;
    final long PERIOD_MS = 3000;
    RecyclerView selectedRecycler;
    List<String> certificationText;
    int currentpage = 0;
    CircleIndicator indicator;
    ProgressDialog progressDialog;
    Timer timer;
    List<String> urls;
    TextView moreSelectedVideo, moreVisitedVideo;

    RelativeLayout relativeLayoutFail,relativeLayoutLoading;
    TextView lblFailMessage;
    AppCompatButton btnFailTryAgain;

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
        galleryPager = rootview.findViewById(R.id.Gallerypager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);

        selectedRecycler = rootview.findViewById(R.id.selected_videos);
        selectedRecycler.setLayoutManager(layoutManager);

        mostViewedRecycler = rootview.findViewById(R.id.mostviewed_videos);
        LinearLayoutManager lLayoutManagerMostViewed = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        lLayoutManagerMostViewed.setReverseLayout(true);


        mostViewedRecycler.setLayoutManager(lLayoutManagerMostViewed);

        //FavoriteRecycler = (RecyclerView) rootview.findViewById(R.id.favorite_videos);
        indicator = rootview.findViewById(R.id.indicator);
        moreSelectedVideo = rootview.findViewById(R.id.more_selectedvideo);
        moreVisitedVideo = rootview.findViewById(R.id.more_mostvisitedvideo);

        moreSelectedVideo.setOnClickListener(this);
        moreVisitedVideo.setOnClickListener(this);


        relativeLayoutFail = (RelativeLayout) rootview.findViewById(R.id.layout_fail);
        relativeLayoutLoading=(RelativeLayout)rootview.findViewById(R.id.layout_loading);
        lblFailMessage=rootview.findViewById(R.id.lbl_fail);
        btnFailTryAgain=rootview.findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);



        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");


        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getGallery();
        getSelectedvideos();
        getMostVisitedvideos();


    }

    private void getMostVisitedvideos() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall = fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b", 0, 1, 10, "ViewCount");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if (response.isSuccessful()) {
                    //todo
                    progressDialog.cancel();

                    final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();
                    HomeContentRecyclerAdapter contentRecyclerAdapter = new HomeContentRecyclerAdapter(getContext(), videoContentObjects);
                    mostViewedRecycler.setAdapter(contentRecyclerAdapter);


                    // Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000", response.body().getIsSuccessful().toString());

                }

            }

            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
                //  Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getSelectedvideos() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall = fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b", 0, 1, 10, "LikeCount ");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if (response.isSuccessful()) {
                    //todo
                    progressDialog.cancel();

                    final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();
                    HomeContentRecyclerAdapter contentRecyclerAdapter = new HomeContentRecyclerAdapter(getContext(), videoContentObjects);
                    selectedRecycler.setAdapter(contentRecyclerAdapter);


                    //  Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000", response.body().getIsSuccessful().toString());

                }

            }

            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
                //  Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getGallery() {

        loadingOrFail(true, true);

        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall = fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",
                0, 1, 10, "" +
                        "");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                if (response.isSuccessful()) {
                    if (response.body().getIsSuccessful()) {



                        final List<VideoContentObject> videoContentObjects = response.body().getResult();
                        for (int i = 0; i < videoContentObjects.size(); i++) {
                            String picurl = (videoContentObjects.get(i).getHeaderImageFileAddress());

                            String title = videoContentObjects.get(i).getSubject();
                            certificationText.add(title);
                            urls.add(picurl);
                            urls.size();

                            galleryPager.setAdapter(new HomeViewPagerAdapter(getContext(), urls, certificationText));
                            indicator.setViewPager(galleryPager);


                        }
                        try {
                            //auto slideshow
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentpage == videoContentObjects.size()) {
                                        currentpage = 0;
                                    }
                                    galleryPager.setCurrentItem(currentpage++, true);
                                }
                            };
                            timer = new Timer(); // This will create a new Thread
                            timer.schedule(new TimerTask() { // task to be scheduled

                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, DELAY_MS, PERIOD_MS);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

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
            public void onFailure(Call<ContentResult> call, Throwable t) {
                //   Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

                loadingOrFail(false, true);//fail layout visible
                lblFailMessage.setText(getString(R.string.noConnection));

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fail_try_again:

                getGallery();

                break;

            case R.id.more_selectedvideo:
                Intent intentselected = new Intent(getActivity(), MoreVideoListActivity.class);
                intentselected.putExtra("requiredList", "LikeCount");
                intentselected.putExtra("toolbarTitle","ویدیوهای منتخب");
                startActivity(intentselected);
                break;

            case R.id.more_mostvisitedvideo:
                Intent intentmostview = new Intent(getActivity(), MoreVideoListActivity.class);
                intentmostview.putExtra("requiredList", "ViewCount");
                intentmostview.putExtra("toolbarTitle",getString(R.string.mostviewed_videos));
                startActivity(intentmostview);
                break;
        }
    }

    void loadingOrFail(boolean hasLoading, boolean showing) {
        if (hasLoading)//if is loading layout
        {
            relativeLayoutFail.setVisibility(View.INVISIBLE);
            //layout fail hidden gone
            if (showing) {
            relativeLayoutLoading.setVisibility(View.VISIBLE);
                //layout laoding visible

            } else {
                relativeLayoutLoading.setVisibility(View.INVISIBLE);
                //layout loading hidden
            }
        } else {// if is fail layout

            relativeLayoutLoading.setVisibility(View.INVISIBLE);
            //layout loadign hidden
            if (showing) {
                relativeLayoutFail.setVisibility(View.VISIBLE);
                //layout fail visible
            } else {
                relativeLayoutFail.setVisibility(View.INVISIBLE);

                //layout fail hidden
            }
        }
    }

}




