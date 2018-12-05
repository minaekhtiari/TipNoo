package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hillavas.tipnoo.Models.AddFavoriteBody;
import com.example.hillavas.tipnoo.Models.DeleteFavorite;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.LikeDislikeResults;
import com.example.hillavas.tipnoo.Models.ActionsCountResult;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;
import com.example.hillavas.tipnoo.Tools.PersianUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;
import java.util.List;


import cn.jzvd.JzvdStd;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bookmarkImg, likeImg, shareImg, videoImg, playIcon, backImg;
    JustifiedTextView likeTxt, viewTxt, toolbarTitle,bodyText,videoTitle;
    PersianUtils persianUtils;

    VideoContentObject videoContentObject;
    JzvdStd videoview;


    FlexboxLayout flexBox;
    ChipCloud chipCloud;

    ArrayList<String> demoArray ;

    String shareUrl;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        bookmarkImg = findViewById(R.id.detail_bookmark_img);
        likeImg = findViewById(R.id.detail_like_img);
        shareImg = findViewById(R.id.detail_share_img);
        likeTxt = findViewById(R.id.detail_like_count);
        viewTxt = findViewById(R.id.detail_view_count);
        videoTitle=findViewById(R.id.video_title);
//        videoview = (JzvdStd) findViewById(R.id.video_view);
        videoImg = findViewById(R.id.video_img);
        playIcon = findViewById(R.id.detail_play_icon);
        backImg = findViewById(R.id.back_img);
        toolbarTitle = findViewById(R.id.detail_toolbar_title);
        bodyText=findViewById(R.id.detail_body_txt);
        flexBox=findViewById(R.id.tag_flexbox_layout);


        bookmarkImg.setOnClickListener(this);
        likeImg.setOnClickListener(this);
        shareImg.setOnClickListener(this);
        backImg.setOnClickListener(this);


        videoContentObject = (VideoContentObject) getIntent().getSerializableExtra("video_detail");
        persianUtils = new PersianUtils();
        toolbarTitle.setText(videoContentObject.getSubject());
        videoTitle.setText(videoContentObject.getSubject());
        likeTxt.setText(persianUtils.toFarsi(String.valueOf(videoContentObject.getLikeCount())));
        viewTxt.setText(persianUtils.toFarsi(String.valueOf(videoContentObject.getViewCount())));

        shareUrl="http://apps.hillavas.com:8088/TeepeTo/Api/Content/Share?id="+videoContentObject.getContentId();

        if (videoContentObject.getIsLiked() == true) {
            likeImg.setImageResource(R.drawable.ic_favorite_black_36dp);
        } else {
            likeImg.setImageResource(R.drawable.ic_favorite_border_black_36dp);
        }

        if (videoContentObject.getIsBookmarked() == true) {
            bookmarkImg.setImageResource(R.drawable.ic_bookmark_black_36dp);
        } else {
            bookmarkImg.setImageResource(R.drawable.ic_bookmark_border_black_36dp);

        }
        toolbarTitle.setText(videoContentObject.getSubject());
        bodyText.setText(videoContentObject.getBody());



       ChipCloudConfig config = new ChipCloudConfig()
               .selectMode(ChipCloud.SelectMode.none)
               .uncheckedChipColor(Color.parseColor("#c43423"))
               .uncheckedTextColor(Color.parseColor("#ffffff"))
               .useInsetPadding(true);


      chipCloud = new ChipCloud(this, flexBox, config);
       for(int i=0;i<videoContentObject.getAllTags().size();i++){
         chipCloud.addChips(new String[]{videoContentObject.getAllTags().get(i).getTitle().toString()});
       }
      //chipCloud.addChips("");






        Picasso.with(VideoDetailActivity.this).load(videoContentObject.getHeaderImageFileAddress()).into(videoImg);

        if ((videoContentObject.getVideoFileAddress()).length() > 0) {
            playIcon.setVisibility(View.VISIBLE);


        }



        // String.format(RetroClient.FILE_URL,"video",videoContentObject.getVideoId());
//        videoview.setUp(String.valueOf(videoContentObject.getVideoFileAddress()), "", JzvdStd.SCREEN_WINDOW_NORMAL);
//
//
//        videoview.thumbImageView.setImageURI(Uri.parse("http://cdn.time.ir/Content/media/image/2018/08/61_orig.png"));
//

        getViewedCount();

        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playIcon.setVisibility(View.INVISIBLE);
                videoImg.setVisibility(View.INVISIBLE);
//                videoview.setVisibility(View.VISIBLE);
//                getViewedCount();

                Intent intentVideo = new Intent(Intent.ACTION_VIEW);
                intentVideo.setDataAndType(Uri.parse(videoContentObject.getVideoFileAddress()), "video/*");
                startActivity(intentVideo);

              //  videoview.onStatePlaying();
            }

        });

    }


    public void getLikeDislike() {

        FileApi fileApi = RetroClient.getApiService();
        final Call<LikeDislikeResults> contentResultCall = fileApi.getLikeOrDislike("007b428d-b807-4ccd-a3a8-afdcc0f18d0b", videoContentObject.getContentId());
        contentResultCall.enqueue(new Callback<LikeDislikeResults>() {
            @Override
            public void onResponse(Call<LikeDislikeResults> call, Response<LikeDislikeResults> response) {
                if (response.isSuccessful()) {

                    if (videoContentObject.getIsLiked() == false) {
                        if (response.body().getResult().getIsLiked() == true) {
                            likeImg.setImageResource(R.drawable.ic_favorite_black_36dp);
                            videoContentObject.setIsLiked(true);
                            likeTxt.setText(persianUtils.toFarsi(String.valueOf(response.body().getResult().getTotalLike())));
                        }
                    } else {
                        if (response.body().getResult().getIsLiked() == false) {
                            likeImg.setImageResource(R.drawable.ic_favorite_border_black_36dp);
                            videoContentObject.setIsLiked(false);
                            likeTxt.setText(persianUtils.toFarsi(String.valueOf(response.body().getResult().getTotalLike())));
                        }
                    }


                } else {
                    Toast.makeText(VideoDetailActivity.this, "دوباره تلاش کنید" + response.body(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LikeDislikeResults> call, Throwable t) {
                Toast.makeText(VideoDetailActivity.this, "اینترنت وصل نیست!", Toast.LENGTH_SHORT).show();
                Log.d("---000", t.toString());
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (JzvdStd.backPress()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_bookmark_img:
                if (videoContentObject.getIsBookmarked() == false) {
                    addFavorite();
                } else {
                    deleteFavorite();

                }
                break;
            case R.id.detail_share_img:

                try {
                    //  if(EncryptedContentId != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                //    sendIntent.putExtra(Intent.EXTRA_TEXT, videoContentObject.getTeaserFileAddress());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    // }
                } catch (Exception e) {
                }
                break;
            case R.id.detail_like_img:
                getLikeDislike();
                break;
            case R.id.back_img:
                onBackPressed();
                break;
        }
    }


    public void getViewedCount() {

        FileApi fileApi = RetroClient.getApiService();
        final Call<ActionsCountResult> contentResultCall = fileApi.getViewCount(videoContentObject.getContentId());
        contentResultCall.enqueue(new Callback<ActionsCountResult>() {
            @Override
            public void onResponse(Call<ActionsCountResult> call, Response<ActionsCountResult> response) {
                if (response.isSuccessful()) {
//                      viewTxt.setText(response.body().getResult()+"");
                } else {

                }
            }

            @Override
            public void onFailure(Call<ActionsCountResult> call, Throwable t) {

            }
        });
    }


    public void addFavorite() {
        AddFavoriteBody addFavoriteBody = new AddFavoriteBody();
        addFavoriteBody.setToken("007b428d-b807-4ccd-a3a8-afdcc0f18d0b");
        addFavoriteBody.setContentId(videoContentObject.getContentId());


        FileApi fileApi = RetroClient.getApiService();
        final Call<ActionsCountResult> contentResultCall = fileApi.AddFavorite(addFavoriteBody);
        contentResultCall.enqueue(new Callback<ActionsCountResult>() {
            @Override
            public void onResponse(Call<ActionsCountResult> call, Response<ActionsCountResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().getIsSuccessful() == true) {
                        videoContentObject.setIsBookmarked(true);
                        bookmarkImg.setImageResource(R.drawable.ic_bookmark_black_36dp);
                        videoContentObject.setFavoriteId(response.body().getResult());
                        Toast.makeText(VideoDetailActivity.this, "به لیست علاقمندی ها افزوده شد", Toast.LENGTH_LONG).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ActionsCountResult> call, Throwable t) {

            }
        });
    }


    public void deleteFavorite() {
        FileApi fileApi = RetroClient.getApiService();
        Call<DeleteFavorite> deleteFavoriteCall = fileApi.deleteFavorite(videoContentObject.getFavoriteId());
        deleteFavoriteCall.enqueue(new Callback<DeleteFavorite>() {
            @Override
            public void onResponse(Call<DeleteFavorite> call, Response<DeleteFavorite> response) {
                if (response.isSuccessful()) {
                    if (response.body().getIsSuccessful() == true) {
                        videoContentObject.setIsBookmarked(false);
                        videoContentObject.setFavoriteId(0);
                        bookmarkImg.setImageResource(R.drawable.ic_bookmark_border_black_36dp);
                        Toast.makeText(VideoDetailActivity.this, "از لیست علاقمندی ها حذف شد", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteFavorite> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {

        videoImg.setVisibility(View.VISIBLE);
        playIcon.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}



