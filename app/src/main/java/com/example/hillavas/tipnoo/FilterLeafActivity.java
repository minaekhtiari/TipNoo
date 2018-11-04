package com.example.hillavas.tipnoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.HomeContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterLeafActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    int categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_leaf);
        recyclerView=findViewById(R.id.filter_leaf_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterLeafActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        categoryId= (int) bundle.get("categoryId");

      getFilteredLeafList();

        Toast.makeText(FilterLeafActivity.this, categoryId+"Don't has childe", Toast.LENGTH_SHORT).show();
    }

    private void getFilteredLeafList() {

        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",categoryId,1,10,"ViewCount");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if(response.isSuccessful()){
                    //todo

                    final ArrayList<ContentList> contentLists= (ArrayList<ContentList>) response.body().getResult();
                    HomeContentRecyclerAdapter contentRecyclerAdapter = new HomeContentRecyclerAdapter(FilterLeafActivity.this, contentLists);
                    recyclerView.setAdapter(contentRecyclerAdapter);



//                    Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
//                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
            //    Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }
}
