package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MoreVideoListActivity extends AppCompatActivity implements View.OnClickListener{
    String requiredList;
    RecyclerView recyclerViewmorelistvideos;
    ImageView moreBack;
    TextView toolbarTitle;
    RelativeLayout relativeLayoutFail,relativeLayoutLoading;
    TextView lblFailMessage;
     Button btnFailTryAgain;
     NestedScrollView nestedScrollView;
     public    int pageNumber ;
    ArrayList<VideoContentObject> videoContentObjects;
    GridLayoutManager layoutManager;
    ContentRecyclerAdapter contentRecyclerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_video_list);


        recyclerViewmorelistvideos=findViewById(R.id.morelist_videos);
        final GridLayoutManager layoutManager = new GridLayoutManager(MoreVideoListActivity.this, 1);
       recyclerViewmorelistvideos.setLayoutManager(layoutManager);
        videoContentObjects=new ArrayList<>();


        recyclerViewmorelistvideos.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int pageNumber) {

                loadMore(pageNumber);
       //  Toast.makeText(MoreVideoListActivity.this,"page"+pageNumber,Toast.LENGTH_SHORT).show();
            }
        });

        toolbarTitle=findViewById(R.id.more_toolbar_title);
        moreBack=findViewById(R.id.more_back);

        moreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle=getIntent().getExtras();
        requiredList=bundle.getString("requiredList");
        toolbarTitle.setText(bundle.getString("toolbarTitle"));
        relativeLayoutFail = (RelativeLayout) findViewById(R.id.layout_fail);
        relativeLayoutLoading=(RelativeLayout)findViewById(R.id.layout_loading);
        lblFailMessage=findViewById(R.id.lbl_fail);
        btnFailTryAgain=findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);
        contentRecyclerAdapter = new ContentRecyclerAdapter(MoreVideoListActivity.this, videoContentObjects);
        recyclerViewmorelistvideos.setAdapter(contentRecyclerAdapter);
//        getList();
    loadMore(1);


//        nestedScrollView.setNestedScrollingEnabled(false);
//        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//
//            @Override
//            public void onScrollChanged() {
//                View view = (View)nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
//
//                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
//                        .getScrollY()));
//
//                if (diff == 0) {
//
//            //    Toast.makeText(MoreVideoListActivity.this,"sjhjkadddd",Toast.LENGTH_SHORT).show();
//                    loadMore();
//
//
//                    // your pagination code
//                }
//            }
//
//
//        });



    }
    private void loadMore(int pg) {


        loadingOrFail(true, true);
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0, pg,50, requiredList);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {



                if(response.isSuccessful()) {
                    if (response.body().getResult().size() > 0){
                        if (response.body().getIsSuccessful()) {


                            videoContentObjects.addAll(response.body().getResult());


                            contentRecyclerAdapter.notifyDataSetChanged();


                            loadingOrFail(true, false);

                        } else {

                            loadingOrFail(false, true);//fail layout visible
                            lblFailMessage.setText(String.valueOf(response.body().getMessage()));
                        }

                }else{
                        Toast.makeText(MoreVideoListActivity.this,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        loadingOrFail(true, false);
                    }
                }else
                {
                    loadingOrFail(false, true);//fail layout visible
                    lblFailMessage.setText(getString(R.string.serverError));
                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
                loadingOrFail(false, true);//fail layout visible
                lblFailMessage.setText(getString(R.string.noConnection));

            }
        });

      // pageNumber=pageNumber+1;

    }

    private void getList() {

        loadingOrFail(true, true);
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,20, requiredList);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {



                if(response.isSuccessful()){

                     if(  response.body().getIsSuccessful()) {
                         final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();



                         ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(MoreVideoListActivity.this, videoContentObjects);
                         recyclerViewmorelistvideos.setAdapter(contentRecyclerAdapter);
                         contentRecyclerAdapter.notifyDataSetChanged();



                         loadingOrFail(true, false);

                     }else{

                         loadingOrFail(false, true);//fail layout visible
                         lblFailMessage.setText(String.valueOf(response.body().getMessage()));
                     }
                }else
                {
                    loadingOrFail(false, true);//fail layout visible
                    lblFailMessage.setText(getString(R.string.serverError));
                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
                loadingOrFail(false, true);//fail layout visible
                lblFailMessage.setText(getString(R.string.noConnection));

            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fail_try_again:
              //getList();
                loadMore(1);
                pageNumber=1;

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
