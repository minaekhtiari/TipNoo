package com.example.hillavas.tipnoo.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.hillavas.tipnoo.EndlessRecyclerOnScrollListener;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.MoreVideoListActivity;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SubCategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerContent;
    int categoryId;
    TextView textView2;
    FragmentActivity activity;
    public ArrayList<VideoContentObject> videoContentObjects;
    ContentRecyclerAdapter contentRecyclerAdapter;
    private SwipeRefreshLayout swipeContainer;
    SubCategoryFragment subCategoryFragment;
    public    int pageNumber ;



    public SubCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onResume() {
        super.onResume();

          getContent(1);
       
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


        // Reload current fragment
//        Fragment frg = null;
//        frg = getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_sub_category, container, false);
        GridLayoutManager lLayout = new GridLayoutManager(getContext(),1);
        recyclerContent=v.findViewById(R.id.recyclerView);
        recyclerContent.setHasFixedSize(true);
        recyclerContent.setLayoutManager(lLayout);
        swipeContainer = v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);

  recyclerContent.setOnScrollListener(new EndlessRecyclerOnScrollListener(lLayout) {
      @Override
      public void onLoadMore(int pageNumber) {

          getContent(pageNumber);
          //Toast.makeText(getContext(),"page"+pageNumber,Toast.LENGTH_SHORT).show();
      }
  });

        videoContentObjects =new ArrayList<>();
        contentRecyclerAdapter = new ContentRecyclerAdapter(getContext(), videoContentObjects);
        recyclerContent.setAdapter(contentRecyclerAdapter);
        return v;
    }


    public void getContent(int pg ){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // categoryId = bundle.getParcelableArrayList("argContent");
            categoryId=bundle.getInt("argContent");
         FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi.getContent
                ("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",categoryId,pg,10,"LastItem");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {

                if(response.isSuccessful()){
                   List<VideoContentObject> Lists=response.body().getResult();

                   videoContentObjects.addAll(Lists);
                    contentRecyclerAdapter.notifyDataSetChanged();


                    Log.d("---000",response.body().getIsSuccessful().toString());
                }
                swipeContainer.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
             //   Toast.makeText(getContext(),""+t,Toast.LENGTH_SHORT).show();
                Log.d("---000",t.toString());
            }
        });

    }

    }
    @Override
    public void onAttach(Activity activity) {
        this.activity= (FragmentActivity) activity;
        super.onAttach(activity);

    }

    @Override
    public void onRefresh() {
      getContent(pageNumber);
    }
}
