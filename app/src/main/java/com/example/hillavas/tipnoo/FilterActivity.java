package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.FilterContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.TabList;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
ListView filterrecyclerView;
    Queue<Integer> filterQueue;
    List<TabList> contentLists;
    int categoryId;
    ImageView filterBack;
    FilterContentRecyclerAdapter contentRecyclerAdapter;


    RelativeLayout relativeLayoutFail,relativeLayoutLoading;
    TextView lblFailMessage;
    Button btnFailTryAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        filterrecyclerView=(ListView) findViewById(R.id.filter_recycler);
        contentLists=new ArrayList<>();
        filterBack=findViewById(R.id.filter_back);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
//
//        filterrecyclerView.setLayoutManager(layoutManager);

        relativeLayoutFail = (RelativeLayout) findViewById(R.id.layout_fail);
        relativeLayoutLoading=(RelativeLayout)findViewById(R.id.layout_loading);
        lblFailMessage=findViewById(R.id.lbl_fail);
        btnFailTryAgain=findViewById(R.id.btn_fail_try_again);
        btnFailTryAgain.setOnClickListener(this);


       filterQueue=new LinkedList<>();
       ((LinkedList<Integer>) filterQueue).add(0,0);

        getAllContent(((LinkedList<Integer>) filterQueue).getLast());
        filterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterQueue.size()>1){
                    ((LinkedList<Integer>) filterQueue).removeLast();
                    getAllContent(((LinkedList<Integer>) filterQueue).getLast());}
                else {
                    onBackPressed();
                }

            }
        });

        filterrecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  categoryId= contentLists.get(position).getCategoryId();
                  if(contentLists.get(position).getHasChild()==true){
                      ((LinkedList<Integer>) filterQueue).add(1,categoryId);
                      getAllContent(categoryId);

                  }else {
                      categoryId=contentLists.get(position).getCategoryId();
                      Intent intent=new Intent(FilterActivity.this,FilterLeafActivity.class);
                      intent.putExtra("categoryId",categoryId);
                      startActivity(intent);


                  }
              //  Toast.makeText(FilterActivity.this, categoryId+"", Toast.LENGTH_SHORT).show();
            }
        });

    }





    public void getAllContent(int q) {
        loadingOrFail(true, true);
        FileApi fileApi = RetroClient.getApiService();
        retrofit2.Call<TabResults> tabResultsCall = fileApi.getAlleContent(q);
        tabResultsCall.enqueue(new Callback<TabResults>() {
            @Override
            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {

                if(response.isSuccessful()){

                    if(response.body().getIsSuccessful()) {
                        contentLists = response.body().getResult();
                        contentRecyclerAdapter =
                                new FilterContentRecyclerAdapter(FilterActivity.this, contentLists);
                        filterrecyclerView.setAdapter(contentRecyclerAdapter);

                        contentRecyclerAdapter.notifyDataSetChanged();
                        loadingOrFail(true, false);
                    }
                    else {
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
            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_fail_try_again:

               getAllContent(((LinkedList<Integer>) filterQueue).getLast());
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
