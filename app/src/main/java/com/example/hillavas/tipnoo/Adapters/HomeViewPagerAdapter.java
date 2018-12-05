package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;
import com.example.hillavas.tipnoo.VideoDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewPagerAdapter extends PagerAdapter {

    Context c;
    TextView textView;
    private List<String> _imagePaths;
    private List<String> _imgnames;
    private LayoutInflater inflater;
   List<VideoContentObject> videoContentObjects;


    public HomeViewPagerAdapter(Context c, List<String> imagePaths, List<String> imgnames)  {
        this._imagePaths = imagePaths;
        this._imgnames=imgnames;
        this.c = c;
    }


    @Override
    public int getCount() {

        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imgDisplay;
        inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.home_pager_item, container,
                false);
        getGallery();

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(context,"test"+getAdapterPosition(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, VideoDetailActivity.class);
                // Bundle bundle = new Bundle();
                intent.putExtra("video_detail",videoContentObjects.get(position));
//            intent.putExtra("isLike",videoContentObjects.get(getAdapterPosition()).getIsLiked());
//            intent.putExtra("isBookmark",videoContentObjects.get(getAdapterPosition()).getIsBookmarked());
//            intent.putExtra("likeCount",videoContentObjects.get(getAdapterPosition()).getLikeCount());
//            intent.putExtra("viewCount",videoContentObjects.get(getAdapterPosition()).getViewCount());
//            intent.putExtra("videoId",videoContentObjects.get(getAdapterPosition()).getVideoId());
//            intent.putExtra("HeaderImageId",videoContentObjects.get(getAdapterPosition()).getHeaderImageId());
//            intent.putExtra("ContentId",videoContentObjects.get(getAdapterPosition()).getContentId());

                c.startActivity(intent);

            }
        });
        textView=viewLayout.findViewById(R.id.textview_home_viewpager);
        imgDisplay =  viewLayout.findViewById(R.id.image_home_viewpager);
        Picasso.with(c).load(_imagePaths.get(position)).into(imgDisplay);
        (container).addView(viewLayout);
        textView.setText(_imgnames.get(position));


                     return viewLayout;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);
    }




//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }


    public void getGallery() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall = fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b", 0, 1, 10, "LastItem");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {


                if (response.isSuccessful()) {
                    if(  response.body().getIsSuccessful()) {


                        videoContentObjects = response.body().getResult();
                        for (int i = 0; i < videoContentObjects.size(); i++) {


                        }


                    }else{
                        Toast.makeText(c, String.valueOf(response.body().getMessage()),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(c,R.string.serverError,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
                Toast.makeText(c,R.string.noConnection,Toast.LENGTH_SHORT).show();
            }
        });


    }
}

