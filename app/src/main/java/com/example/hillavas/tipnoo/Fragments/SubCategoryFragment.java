package com.example.hillavas.tipnoo.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SubCategoryFragment extends Fragment {
    RecyclerView recyclerContent;
    int categoryId;
    TextView textView2;
    FragmentActivity activity;
    public ArrayList<VideoContentObject> videoContentObjects;
    ContentRecyclerAdapter contentRecyclerAdapter;

    public ArrayList<VideoContentObject> rcyclDatas;
    public SubCategoryFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
//        if(menuVisible){
//            getContent();
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
        getContent();
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
//        textView2=v.findViewById(R.id.textView2);

        return v;
    }


    public void getContent(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // categoryId = bundle.getParcelableArrayList("argContent");
            categoryId=bundle.getInt("argContent");
            //Toast.makeText(getContext(),categoryId+"",Toast.LENGTH_SHORT).show();
         //   textView2.setText(categoryId+"");
          //  rcycleList();
        FileApi fileApi = RetroClient.getApiService();
        final Call<ContentResult> contentResultCall=fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",categoryId,1,10,"LastItem");
        contentResultCall.enqueue(new Callback<ContentResult>() {
            @Override
            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {
              response.body().getIsSuccessful();
                if(response.isSuccessful()){
                   List<VideoContentObject> Lists=response.body().getResult();
                   videoContentObjects.clear();
                   videoContentObjects.addAll(Lists);
                    contentRecyclerAdapter.notifyDataSetChanged();
                  //  Toast.makeText(getContext(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());
                }
//                    for (int i=0;i<response.body().getResult().size();i++){
              //  Log.d("---000",response.body().getIsSuccessful().toString());
//
//                    }
            }
            @Override
            public void onFailure(Call<ContentResult> call, Throwable t) {
             //   Toast.makeText(getContext(),""+t,Toast.LENGTH_SHORT).show();
                Log.d("---000",t.toString());
            }
        });

        videoContentObjects =new ArrayList<>();
        contentRecyclerAdapter = new ContentRecyclerAdapter(getContext(), videoContentObjects);
        recyclerContent.setAdapter(contentRecyclerAdapter);
    }

    }
    @Override
    public void onAttach(Activity activity) {
        this.activity= (FragmentActivity) activity;
        super.onAttach(activity);

    }

}
