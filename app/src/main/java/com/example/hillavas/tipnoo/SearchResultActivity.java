package com.example.hillavas.tipnoo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList selectedTags;
    RecyclerView searchrecyclerView;
    ImageView searchResultBack;


    RelativeLayout relativeLayoutFail,relativeLayoutLoading;
    TextView lblFailMessage;
    Button btnFailTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
         searchrecyclerView=findViewById(R.id.search_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultActivity.this, LinearLayoutManager.VERTICAL, false);
        searchrecyclerView.setLayoutManager(layoutManager);
        searchResultBack=findViewById(R.id.search_result_backspace);
        Bundle bundle = getIntent().getExtras();
        selectedTags=bundle.getParcelableArrayList("selectedChips");


        relativeLayoutFail = (RelativeLayout) findViewById(R.id.layout_fail);
        relativeLayoutLoading=(RelativeLayout)findViewById(R.id.layout_loading);
        lblFailMessage=findViewById(R.id.lbl_fail);
        btnFailTryAgain=findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);

        getSearchResultdContent();

        searchResultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void getSearchResultdContent() {

        loadingOrFail(true, true);

        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi.getWithTagIdsContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",
                10,1,10,"LastItem",selectedTags);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {
                if (response.isSuccessful()){

                    if(  response.body().getIsSuccessful()) {

                        response.body().getResult();
                        final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();
                        ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(SearchResultActivity.this, videoContentObjects);
                        searchrecyclerView.setAdapter(contentRecyclerAdapter);

                        loadingOrFail(true, false);

                    }
                    else
                    {
                        loadingOrFail(false, true);//fail layout visible
                        lblFailMessage.setText(String.valueOf(response.body().getMessage()));
                    }
                }else{
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

               getSearchResultdContent();
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
