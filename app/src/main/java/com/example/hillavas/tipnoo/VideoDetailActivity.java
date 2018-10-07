package com.example.hillavas.tipnoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailActivity extends AppCompatActivity {
private List<ContentList> contentLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        getContent();
    }
        public void getContent(){

                FileApi fileApi = RetroClient.getApiService();
                final Call<ContentResult> contentResultCall=fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",10,1,10,"LastItem");
                contentResultCall.enqueue(new Callback<ContentResult>() {
                    @Override
                    public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                        response.body().getIsSuccessful();

                        if(response.isSuccessful()){
                            contentLists=response.body().getResult();
                            Toast.makeText(VideoDetailActivity.this,""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                            Log.d("---000",response.body().getIsSuccessful().toString());

                        }
//                    for (int i=0;i<response.body().getResult().size();i++){
                        //  Log.d("---000",response.body().getIsSuccessful().toString());
//
//                    }
                    }
                    @Override
                    public void onFailure(Call<ContentResult> call, Throwable t) {
                        Toast.makeText(VideoDetailActivity.this,""+t,Toast.LENGTH_SHORT).show();
                        Log.d("---000",t.toString());
                    }
                });


            }
        }



