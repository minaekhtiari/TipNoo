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
import com.example.hillavas.tipnoo.Models.LikeDislikeBody;
import com.example.hillavas.tipnoo.Models.LikeDislikeResults;
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
ImageView bookmarkImg, likeImg, shareImg, videoImg,playIcon;
TextView likeTxt,viewTxt;

Boolean isLike,isBookmark;
int likeCount,viewCount,contentId;

String videoId,teaserId;
    JzvdStd videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        bookmarkImg=findViewById(R.id.detail_bookmark_img);
        likeImg=findViewById(R.id.detail_like_img);
         shareImg=findViewById(R.id.detail_share_img);
        likeTxt=findViewById(R.id.detail_like_count);
        viewTxt=findViewById(R.id.detail_view_count);
        videoview = (JzvdStd) findViewById(R.id.video_view);
         videoImg=findViewById(R.id.video_img);
         playIcon=findViewById(R.id.detail_play_icon);


         bookmarkImg.setOnClickListener(this);
         likeImg.setOnClickListener(this);
         shareImg.setOnClickListener(this);



        Bundle bundle=getIntent().getExtras();
      isLike= (Boolean) bundle.get("isLike");
      isBookmark=(Boolean)bundle.get("isBookmark") ;
      likeCount= (int) bundle.get("likeCount");
      viewCount= (int) bundle.get("viewCount");
      videoId= (String) bundle.get("videoId");
      teaserId= (String) bundle.get("HeaderImageId");
      contentId= (int) bundle.get("ContentId");

        likeTxt.setText(likeCount+"");
       viewTxt.setText(viewCount+"");
        if(isLike==true){
            likeImg.setImageResource(R.drawable.ic_favorite_black_36dp);
        }else{
            likeImg.setImageResource(R.drawable.ic_favorite_border_black_36dp);
        }

        if(isBookmark==true){
            bookmarkImg.setImageResource(R.drawable.ic_bookmark_black_36dp);
        }else {
            bookmarkImg.setImageResource(R.drawable.ic_bookmark_border_black_36dp);

        }

        Picasso.with(VideoDetailActivity.this).load(
                "http://79.175.138.77:7091/file/getfile?FileType=image&fileid="+teaserId).into(videoImg);

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playIcon.setVisibility(View.INVISIBLE);
                videoImg.setVisibility(View.INVISIBLE);
                videoview.setVisibility(View.VISIBLE);


                videoview.setUp(("http://79.175.138.77:7091/file/getfile?FileType=video&fileid="+videoId)
                        , "" , Jzvd.SCREEN_WINDOW_NORMAL);


            }
        });


    }
        public void getLikeDislike(){
            LikeDislikeBody likeDislikeBody=new LikeDislikeBody();
            likeDislikeBody.setToken("007b428d-b807-4ccd-a3a8-afdcc0f18d0b");
            likeDislikeBody.setContentId(contentId);
                FileApi fileApi = RetroClient.getApiService();
                final Call<LikeDislikeResults> contentResultCall=fileApi.getLikeOrDislike(likeDislikeBody);
                contentResultCall.enqueue(new Callback<LikeDislikeResults>() {
                    @Override
                    public void onResponse(Call<LikeDislikeResults> call, Response<LikeDislikeResults> response) {
                        if(response.isSuccessful()){
                     //   Toast.makeText(VideoDetailActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else {

                        }

                    }

                    @Override
                    public void onFailure(Call<LikeDislikeResults> call, Throwable t) {
                      //  Toast.makeText(VideoDetailActivity.this,""+t,Toast.LENGTH_SHORT).show();
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
        switch (v.getId()) {
            case R.id.detail_bookmark_img:
                if (isBookmark == true) {
                    bookmarkImg.setImageResource(R.drawable.ic_bookmark_black_36dp);
                } else {
                    bookmarkImg.setImageResource(R.drawable.ic_bookmark_border_black_36dp);

                }
                break;
            case R.id.detail_share_img:

                try {
                    //  if(EncryptedContentId != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.time.ir/");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    // }
                } catch (Exception e) {
                }
                break;
            case R.id.detail_like_img:
                if (isLike == true) {
                    getLikeDislike();
                    likeImg.setImageResource(R.drawable.ic_favorite_black_36dp);


                } else {
                    getLikeDislike();
                    likeImg.setImageResource(R.drawable.ic_favorite_border_black_36dp);

                    break;
                }
        }
    }
}



