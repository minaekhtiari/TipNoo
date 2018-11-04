package com.example.hillavas.tipnoo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.HomeContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.SearchContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchResultActivity extends AppCompatActivity {
    ArrayList selectedTags;
    RecyclerView searchrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
         searchrecyclerView=findViewById(R.id.search_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchResultActivity.this, LinearLayoutManager.VERTICAL, false);
        searchrecyclerView.setLayoutManager(layoutManager);
        Bundle bundle = getIntent().getExtras();
        selectedTags=bundle.getParcelableArrayList("selectedChips");
        getSearchResultdContent();
    }


    private void getSearchResultdContent() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi.getWithTagIdsContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",
                10,1,10,"LastItem",selectedTags);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {
                if (response.isSuccessful()){
                    response.body().getIsSuccessful();
                    response.body().getResult();
                    final ArrayList <ContentList> contentLists= (ArrayList<ContentList>) response.body().getResult();
                    ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(SearchResultActivity.this, contentLists);
                    searchrecyclerView.setAdapter(contentRecyclerAdapter);


                   // Toast.makeText(SearchResultActivity.this,""+ response.isSuccessful(),Toast.LENGTH_SHORT).show();

                }else{
                 //   Toast.makeText(SearchResultActivity.this,""+ response.isSuccessful(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
               // Toast.makeText(SearchResultActivity.this,""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
