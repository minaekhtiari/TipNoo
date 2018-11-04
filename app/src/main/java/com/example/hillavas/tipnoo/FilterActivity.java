package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.Adapters.FilterContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.TabList;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends AppCompatActivity {
ListView filterrecyclerView;
    Queue<Integer> filterQueue;
    List<TabList> contentLists;
    int categoryId;
    FilterContentRecyclerAdapter contentRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        filterrecyclerView=(ListView) findViewById(R.id.filter_recycler);
        contentLists=new ArrayList<>();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(FilterActivity.this, LinearLayoutManager.VERTICAL, false);
//
//        filterrecyclerView.setLayoutManager(layoutManager);

       filterQueue=new LinkedList<>();
       ((LinkedList<Integer>) filterQueue).add(0,0);

        getAllContent(((LinkedList<Integer>) filterQueue).getLast());

        filterrecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  categoryId= contentLists.get(position).getCategoryId();
                  if(contentLists.get(position).getHasChild()==true){
                      ((LinkedList<Integer>) filterQueue).add(1,categoryId);
                      getAllContent(categoryId);

                  }else {
                      categoryId=contentLists.get(position).getCategoryId();
                      Intent intent=new Intent(FilterActivity.this,FilterLeafActivity.class);
                      intent.putExtra("categoryId",categoryId);
                      startActivity(intent);
                      Toast.makeText(FilterActivity.this, categoryId+"Don't has childe", Toast.LENGTH_SHORT).show();
                  }
                Toast.makeText(FilterActivity.this, categoryId+"", Toast.LENGTH_SHORT).show();
            }
        });

    }





    public void getAllContent(int q) {

        FileApi fileApi = RetroClient.getApiService();
        retrofit2.Call<TabResults> tabResultsCall = fileApi.getAlleContent(q);
        tabResultsCall.enqueue(new Callback<TabResults>() {
            @Override
            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {

                if(response.isSuccessful()){
                  contentLists=  response.body().getResult();
                    contentRecyclerAdapter =
                            new FilterContentRecyclerAdapter(FilterActivity.this, contentLists);
                    filterrecyclerView.setAdapter(contentRecyclerAdapter);

                    contentRecyclerAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
//        if(filterQueue.size()>1){
//          ((LinkedList<Integer>) filterQueue).removeLast();
//        getAllContent(((LinkedList<Integer>) filterQueue).getLast());}
//        else {
//            onBackPressed();
//        }
//
   }
}
