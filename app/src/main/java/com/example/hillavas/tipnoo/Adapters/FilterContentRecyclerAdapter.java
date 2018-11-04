package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hillavas.tipnoo.FilterActivity;
import com.example.hillavas.tipnoo.Models.TabList;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.Models.TagList;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FilterContentRecyclerAdapter extends ArrayAdapter<TabList> {
    private List<TabList> contentLists;
    Context context;
public FilterContentRecyclerAdapter(Context context, List<TabList> contentLists) {
        super(context, 0, contentLists);
        this.context=context;
        this.contentLists=contentLists;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        TabList tabList=getItem(position);
        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_rcyclersearch, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(tabList.getName());


        return convertView;
        }
        }
//   RecyclerView.Adapter<FilterContentRecyclerAdapter.ViewHolder> {
//    private List<TabList> contentLists;
//    Context context;
//    RecyclerView recyclerView;
//    private HomeContentRecyclerAdapter.ItemClickListener mClickListener;
//
//    public FilterContentRecyclerAdapter(Context context, List<TabList> contentLists) {
//        this.context=context;
//        this.contentLists=contentLists;
//    }
//
//    @NonNull
//    @Override
//    public FilterContentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View v=  LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.card_view_rcyclersearch,viewGroup, false);
//        return new ViewHolder(v) ;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FilterContentRecyclerAdapter.ViewHolder holder, int position) {
//        TabList contentListPositon=contentLists.get(position);
//        holder.title.setText(contentListPositon.getName());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return contentLists.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        TextView title;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//           title=itemView.findViewById(R.id.title);
//           recyclerView=itemView.findViewById(R.id.testrecycl);
//            itemView.setOnClickListener(this);
//
//        }
//
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//
//          int categoryId= contentLists.get(getAdapterPosition()).getCategoryId();
//            Toast.makeText(context,categoryId+"",Toast.LENGTH_LONG).show();
////            if( contentLists.get(getAdapterPosition()).getHasChild()==true){
////
////                if(contentLists.get(getAdapterPosition()).getParentId()!=null){
////                       getAllContent();
////                }else{
////                    contentLists.get(getAdapterPosition()).getCategoryId();
////                }
////            }
//
//        }
//    }
//
//    void setClickListener(HomeContentRecyclerAdapter.ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//    public void getAllContent() {
//
//        FileApi fileApi = RetroClient.getApiService();
//        retrofit2.Call<TabResults> tabResultsCall = fileApi.getAlleContent(0);
//        tabResultsCall.enqueue(new Callback<TabResults>() {
//            @Override
//            public void onResponse(retrofit2.Call<TabResults> call, Response<TabResults> response) {
//
//                if(response.isSuccessful()){
//                    List<TabList> contentLists=  response.body().getResult();
//                    FilterContentRecyclerAdapter contentRecyclerAdapter = new FilterContentRecyclerAdapter(context, contentLists);
//                  //  filterrecyclerView.setAdapter(contentRecyclerAdapter);
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<TabResults> call, Throwable t) {
//
//            }
//        });
//    }
//}