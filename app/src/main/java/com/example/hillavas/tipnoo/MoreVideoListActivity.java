package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
    RecyclerView morelistvideos;
    ImageView moreBack;
    TextView toolbarTitle;
    RelativeLayout relativeLayoutFail,relativeLayoutLoading;
    TextView lblFailMessage;
     Button btnFailTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_video_list);

        morelistvideos=findViewById(R.id.morelist_videos);
        GridLayoutManager layoutManager = new GridLayoutManager(MoreVideoListActivity.this, 1);
        morelistvideos.setLayoutManager(layoutManager);
        toolbarTitle=findViewById(R.id.more_toolbar_title);
        moreBack=findViewById(R.id.more_back);
        moreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle=getIntent().getExtras();
        String requiredList=bundle.getString("requiredList");
        toolbarTitle.setText(bundle.getString("toolbarTitle"));
        relativeLayoutFail = (RelativeLayout) findViewById(R.id.layout_fail);
        relativeLayoutLoading=(RelativeLayout)findViewById(R.id.layout_loading);
        lblFailMessage=findViewById(R.id.lbl_fail);
        btnFailTryAgain=findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);

        getList();

    }


    private void getList() {

        loadingOrFail(true, true);
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,10, requiredList);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {



                if(response.isSuccessful()){

                     if(  response.body().getIsSuccessful()) {
                         final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();
                         ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(MoreVideoListActivity.this, videoContentObjects);
                         morelistvideos.setAdapter(contentRecyclerAdapter);

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
              getList();

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
