package com.example.hillavas.tipnoo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.appunite.appunitevideoplayer.PlayerActivity;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {
private List<ContentList> contentLists;
ImageView bookmarkimg
   , likeimg
    , shareimg
    , videoimg,playicon;
TextView likecount,viewcount;
VideoView videoView;
Boolean isLike,isBookmark;
int likeCount,viewCount;
URL imgUrl;
String videoId,teaserId;
    JzvdStd videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        bookmarkimg=findViewById(R.id.detail_bookmark_img);
        likeimg=findViewById(R.id.detail_like_img);
         shareimg=findViewById(R.id.detail_share_img);
        likecount=findViewById(R.id.detail_like_count);
        viewcount=findViewById(R.id.detail_view_count);
        videoview = (JzvdStd) findViewById(R.id.video_view);
         videoimg=findViewById(R.id.video_img);
         playicon=findViewById(R.id.detail_play_icon);


         bookmarkimg.setOnClickListener(this);
         likeimg.setOnClickListener(this);
         shareimg.setOnClickListener(this);



        Bundle bundle=getIntent().getExtras();
      isLike= (Boolean) bundle.get("isLike");
      isBookmark=(Boolean)bundle.get("isBookmark") ;
      likeCount= (int) bundle.get("likeCount");
      viewCount= (int) bundle.get("viewCount");
      videoId= (String) bundle.get("videoId");
      teaserId= (String) bundle.get("HeaderImageId");



       likecount.setText(likeCount+"");
       viewcount.setText(viewCount+"");

        Picasso.with(VideoDetailActivity.this).load("http://79.175.138.77:7091/file/getfile?FileType=image&fileid="+teaserId).into(videoimg);

        playicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playicon.setVisibility(View.INVISIBLE);
                videoimg.setVisibility(View.INVISIBLE);
                videoview.setVisibility(View.VISIBLE);


                videoview.setUp("https://hw16.cdn.asset.aparat.com/aparat-video/1b80a9e95e4d05a1fd2ac5d77397c59112430440-480p__86448.mp4"
                        , "" , Jzvd.SCREEN_WINDOW_NORMAL);


            }
        });

        getContent();
    }
        public void getContent(){

                FileApi fileApi = RetroClient.getApiService();
                final Call<ContentResult> contentResultCall=fileApi.getContent(
                        "007b428d-b807-4ccd-a3a8-afdcc0f18d0b",10,1,10,"LastItem");
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

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.detail_bookmark_img:
               bookmarkimg.setImageResource(R.drawable.ic_bookmark_fill_24dp);
               break;
           case R.id.detail_share_img:

               try {
                 //  if(EncryptedContentId != null) {
                       Intent sendIntent = new Intent();
                       sendIntent.setAction(Intent.ACTION_SEND);
                       sendIntent.putExtra(Intent.EXTRA_TEXT,"http://www.time.ir/");
                       sendIntent.setType("text/plain");
                       startActivity(sendIntent);
                  // }
               }catch (Exception e){}
               break;
           case R.id.detail_like_img:
            likeimg.setImageResource(R.drawable.ic_like_fill_24dp);
               break;
       }
    }
}



