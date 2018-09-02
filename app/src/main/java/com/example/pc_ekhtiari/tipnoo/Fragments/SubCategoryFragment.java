package com.example.pc_ekhtiari.tipnoo.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc_ekhtiari.bottomnavigationview.R;
import com.example.pc_ekhtiari.tipnoo.Adapters.ContentRecyclerAdapter;
import com.example.pc_ekhtiari.tipnoo.Models.ContentList;


import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;


public class SubCategoryFragment extends Fragment {
    RecyclerView recyclerContent;
    int categoryId;
    TextView textView2;
    public ArrayList<ContentList> contentLists;
    ContentRecyclerAdapter contentRecyclerAdapter;
    Context context;
    public ArrayList<ContentList> rcyclDatas;
    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_sub_category, container, false);
        GridLayoutManager lLayout = new GridLayoutManager(context,1);
        recyclerContent=v.findViewById(R.id.recyclerView);
        recyclerContent.setHasFixedSize(true);
        recyclerContent.setLayoutManager(lLayout);
        textView2=v.findViewById(R.id.textView2);
        getContent();
        return v;
    }


    public void getContent(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // categoryId = bundle.getParcelableArrayList("argContent");
            categoryId=bundle.getInt("argContent");
            //Toast.makeText(getContext(),categoryId+"",Toast.LENGTH_SHORT).show();
            textView2.setText(categoryId+"");
            rcycleList();
//        FileApi fileApi = RetroClient.getApiService();
//        final Call<ContentResult> contentResultCall=fileApi.getContent("007b428d-b807-4ccd-a3a8-afdcc0f18d0b",10,1,10,"LastItem");
//        contentResultCall.enqueue(new Callback<ContentResult>() {
//            @Override
//            public void onResponse(Call<ContentResult> call, Response<ContentResult> response) {
//              response.body().getIsSuccessful();
//                if(response.isSuccessful()){
//                   List<ContentList> Lists=response.body().getResult();
//                   contentLists.clear();
//                   contentLists.addAll(Lists);
//                    contentRecyclerAdapter.notifyDataSetChanged();
//                    Toast.makeText(getContext(),""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
//                    Log.d("---000",response.body().getIsSuccessful().toString());
//                }
////                    for (int i=0;i<response.body().getResult().size();i++){
//              //  Log.d("---000",response.body().getIsSuccessful().toString());
////
////                    }
//            }
//            @Override
//            public void onFailure(Call<ContentResult> call, Throwable t) {
//                Toast.makeText(getContext(),""+t,Toast.LENGTH_SHORT).show();
//                Log.d("---000",t.toString());
//            }
//        });

//        contentLists=new ArrayList<>();
//        contentRecyclerAdapter = new ContentRecyclerAdapter(context, contentLists);
//        recyclerContent.setAdapter(contentRecyclerAdapter);
        }

    }

    private void rcycleList() {

        rcyclDatas = new ArrayList<>();
        ContentList data1 = new ContentList();
        data1.setSubject("استایل های کاری بانوان");
        data1.setHeaderImageId(R.drawable.ic_crop_free_black_24dp+"");
        ContentList data2 = new ContentList();
        data2.setSubject("استایل بانوان");
        data2.setHeaderImageId(R.drawable.womenstyle2+"");
        ContentList data3 = new ContentList();
        data3.setSubject("data3");
        data3.setHeaderImageId(R.drawable.ic_dashboard_black_24dp+"");

        rcyclDatas.add(data1);
        rcyclDatas.add(data2);
        rcyclDatas.add(data3);



        //  CountryAdapter ca = new CountryAdapter(countryList);
        //  rv.setAdapter(ca);

        contentRecyclerAdapter = new ContentRecyclerAdapter(context, rcyclDatas);
        recyclerContent.setAdapter(contentRecyclerAdapter);
    }

}
