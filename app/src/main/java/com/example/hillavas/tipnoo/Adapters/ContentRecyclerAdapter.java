package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.VideoDetailActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;


public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder> {
    private ArrayList<ContentList> contentLists;
    Context context;
    private ItemClickListener clickListener;

    public  ContentRecyclerAdapter(Context context,ArrayList<ContentList> contentLists){
        this.context=context;
        this.contentLists=contentLists;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v=  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_rcyclecontent,viewGroup, false);
        return new ViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        ContentList contentListPositon=contentLists.get(position);
        viewHolder.title.setText(contentListPositon.getSubject());
        viewHolder.viewcount.setText(contentListPositon.getViewCount()+"");
        viewHolder.likecount.setText(contentListPositon.getLikeCount()+"");
        if((contentListPositon.getIsLiked())==true)
        {

            viewHolder.likeimg.setImageResource(R.drawable.ic_favorite_black_36dp);

        }else{
            viewHolder.likeimg.setImageResource(R.drawable.ic_favorite_border_black_36dp);
        }
        if((contentListPositon.getIsBookmarked())==true)
        {
            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_black_36dp);
        }else{

            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_border_black_36dp);
        }

        Picasso.with(context).load("http://79.175.138.77:7091/file/getfile?FileType=image&fileid="+contentListPositon.getHeaderImageId()).into(viewHolder.contetntImg);

    }

    @Override
    public int getItemCount() {
       return contentLists.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
          TextView title,viewcount,likecount;
          ImageView contetntImg,bookmark,likeimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.content_title);
            contetntImg=itemView.findViewById(R.id.image_view_subcategory_fragment);
            likecount=itemView.findViewById(R.id.like_count);
            bookmark=itemView.findViewById(R.id.bookmark);
            likeimg=itemView.findViewById(R.id.like_img);
            viewcount=itemView.findViewById(R.id.view_count);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
           // Toast.makeText(context,"test"+getAdapterPosition(),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(context,VideoDetailActivity.class);
            intent.putExtra("isLike",contentLists.get(getAdapterPosition()).getIsLiked());
            intent.putExtra("isBookmark",contentLists.get(getAdapterPosition()).getIsBookmarked());
            intent.putExtra("likeCount",contentLists.get(getAdapterPosition()).getLikeCount());
            intent.putExtra("viewCount",contentLists.get(getAdapterPosition()).getViewCount());
            intent.putExtra("videoId",contentLists.get(getAdapterPosition()).getVideoId());
            intent.putExtra("HeaderImageId",contentLists.get(getAdapterPosition()).getHeaderImageId());
            intent.putExtra("ContentId",contentLists.get(getAdapterPosition()).getContentId());
            view.getContext().startActivity(intent);

        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
