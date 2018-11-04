package com.example.hillavas.tipnoo.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


//mport com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.HomeContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.HomeViewPagerAdapter;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.MoreVideoListActivity;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {
    final long DELAY_MS = 10;
    RecyclerView favoriteRecycler;
    ViewPager Gallerypager;
    RecyclerView mostViewedRecycler;
    final long PERIOD_MS = 3000;
    RecyclerView selectedRecycler;
    List<String> certificationText;
    int currentpage = 0;
    CircleIndicator indicator;
    ProgressDialog progressDialog;
    Timer timer;
    List<String> urls;
    TextView moreSelectedVideo,moreVisitedVideo;

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        urls = new ArrayList();
        certificationText = new ArrayList();
        Gallerypager = rootview.findViewById(R.id.Gallerypager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        selectedRecycler =rootview.findViewById(R.id.selected_videos);
        selectedRecycler.setLayoutManager(layoutManager);
        mostViewedRecycler = rootview.findViewById(R.id.mostviewed_videos);
        LinearLayoutManager lLayoutManagerMostViewed= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true );
        lLayoutManagerMostViewed.setReverseLayout(true);
        lLayoutManagerMostViewed.setStackFromEnd(true);
        mostViewedRecycler.setLayoutManager(lLayoutManagerMostViewed);

        //FavoriteRecycler = (RecyclerView) rootview.findViewById(R.id.favorite_videos);
        indicator =  rootview.findViewById(R.id.indicator);
        moreSelectedVideo=rootview.findViewById(R.id.more_selectedvideo);
        moreVisitedVideo=rootview.findViewById(R.id.more_mostvisitedvideo);

        moreSelectedVideo.setOnClickListener(this);
        moreVisitedVideo.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         getGallery();
         getSelectedvideos();
       getMostVisitedvideos();
    }

    private void getMostVisitedvideos() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,10,"ViewCount");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if(response.isSuccessful()){
                    //todo
                    progressDialog.cancel();

                    final ArrayList <ContentList> contentLists= (ArrayList<ContentList>) response.body().getResult();
                    HomeContentRecyclerAdapter contentRecyclerAdapter = new HomeContentRecyclerAdapter(getContext(), contentLists);
                   mostViewedRecycler.setAdapter(contentRecyclerAdapter);


                   // Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
              //  Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getSelectedvideos() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi
                .getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,10,"LikeCount ");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if(response.isSuccessful()){
                    //todo
                    progressDialog.cancel();

                    final ArrayList <ContentList> contentLists= (ArrayList<ContentList>) response.body().getResult();
                    HomeContentRecyclerAdapter contentRecyclerAdapter = new HomeContentRecyclerAdapter(getContext(), contentLists);
                    selectedRecycler.setAdapter(contentRecyclerAdapter);


                  //  Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
              //  Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void getGallery() {
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",0,1,10,"LastItem");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                response.body().getIsSuccessful();

                if(response.isSuccessful()){
                    //todo
                    progressDialog.cancel();

                  final List <ContentList> contentLists=response.body().getResult();
                    for (int i=0;i<contentLists.size();i++) {
                        String picurl ="http://79.175.138.77:7091/file/getfile?FileType=image&fileid="+( contentLists.get(i).getHeaderImageId());

                        String title=contentLists.get(i).getSubject();
                        certificationText.add(title);
                        urls.add(picurl);
                        urls.size();

                        Gallerypager.setAdapter(new HomeViewPagerAdapter(getContext(), urls,certificationText));
                        indicator.setViewPager(Gallerypager);


                    }

                    //auto slideshow
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentpage == contentLists.size()) {
                                currentpage = 0;
                            }
                            Gallerypager.setCurrentItem(currentpage++, true);


                        }
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer .schedule(new TimerTask() { // task to be scheduled

                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    },DELAY_MS, PERIOD_MS);

                    //   contentLists=response.body().getResult();
                  ///  Toast.makeText(getActivity(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
             //   Toast.makeText(getActivity(),""+t,Toast.LENGTH_SHORT).show();
                Log.d("---000",t.toString());
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_selectedvideo:
                Intent intentselected=new Intent(getActivity(),MoreVideoListActivity.class);
                intentselected.putExtra("requiredList","LikeCount");
                startActivity(intentselected);
                break;

            case  R.id.more_mostvisitedvideo:
                Intent intentmostview=new Intent(getActivity(),MoreVideoListActivity.class);
                intentmostview.putExtra("requiredList","ViewCount");
                startActivity(intentmostview);
                break;
        }
    }
}




