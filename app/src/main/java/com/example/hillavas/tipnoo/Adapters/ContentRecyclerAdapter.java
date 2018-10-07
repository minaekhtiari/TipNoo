package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.R;

import java.util.ArrayList;


public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder> {
    private ArrayList<ContentList> contentLists;
    Context context;
    private ItemClickListener mClickListener;

    public  ContentRecyclerAdapter(Context context,ArrayList<ContentList> contentLists){
        this.context=context;
        this.contentLists=contentLists;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v=  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_rcyclecontent,viewGroup, false);
        return new ViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ContentList contentListPositon=contentLists.get(position);
        viewHolder.title.setText(contentListPositon.getSubject());
      //  Picasso.with(context).load(contentListPositon.getHeaderImageId()).into(viewHolder.bookmark);


    }

    @Override
    public int getItemCount() {
       return contentLists.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
          TextView title,likecount,duration;
          ImageView contetntImg,bookmark,share,like,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.content_title);
//            likecount=itemView.findViewById(R.id.likeCount);
//            duration=itemView.findViewById(R.id.timeDuration);
//
//            contetntImg=itemView.findViewById(R.id.content_image);
//            bookmark=itemView.findViewById(R.id.bookMark);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
