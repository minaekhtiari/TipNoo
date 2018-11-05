package com.example.hillavas.tipnoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class MoreVideoListActivity extends AppCompatActivity {
    String requiredList;
    RecyclerView morelistvideos;
    ImageView moreBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_video_list);

        morelistvideos=findViewById(R.id.morelist_videos);
        GridLayoutManager layoutManager = new GridLayoutManager(MoreVideoListActivity.this, 1);
        morelistvideos.setLayoutManager(layoutManager);
        moreBack=findViewById(R.id.more_back);
        moreBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle=getIntent().getExtras();
        String requiredList=bundle.getString("requiredList");

        getList();

    }


    private void getList() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,10, requiredList);
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if(response.isSuccessful()){


                    final ArrayList<VideoContentObject> videoContentObjects = (ArrayList<VideoContentObject>) response.body().getResult();
                    ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(MoreVideoListActivity.this, videoContentObjects);
                    morelistvideos.setAdapter(contentRecyclerAdapter);


                    Toast.makeText(MoreVideoListActivity.this,""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
            //    Toast.makeText(MoreVideoListActivity.this,""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }
}
