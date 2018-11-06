package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.hillavas.bottomnavigationview.R;
import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.VideoDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentRecyclerAdapter.ViewHolder> {
    private ArrayList<VideoContentObject> videoContentObjects;
    Context context;
    private ItemClickListener clickListener;

    public ContentRecyclerAdapter(Context context, ArrayList<VideoContentObject> videoContentObjects) {
        this.context = context;
        this.videoContentObjects = videoContentObjects;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_rcyclecontent, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        VideoContentObject videoContentObjectPositon = videoContentObjects.get(position);
        viewHolder.title.setText(videoContentObjectPositon.getSubject());
        viewHolder.viewcount.setText(videoContentObjectPositon.getViewCount() + "");
        viewHolder.likecount.setText(videoContentObjectPositon.getLikeCount() + "");
        if ((videoContentObjectPositon.getIsLiked()) == true) {

            viewHolder.likeimg.setImageResource(R.drawable.ic_favorite_black_36dp);

        } else {
            viewHolder.likeimg.setImageResource(R.drawable.ic_favorite_border_black_36dp);
        }
        if ((videoContentObjectPositon.getIsBookmarked()) == true) {
            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_black_36dp);
        } else {

            viewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_border_black_36dp);
        }

        Picasso.with(context).load("http://79.175.138.77:7091/file/getfile?FileType=image&fileid=" + videoContentObjectPositon.getHeaderImageId()).into(viewHolder.contetntImg);

    }

    @Override
    public int getItemCount() {
        return videoContentObjects.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, viewcount, likecount;
        ImageView contetntImg, bookmark, likeimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.content_title);
            contetntImg = itemView.findViewById(R.id.image_view_subcategory_fragment);
            likecount = itemView.findViewById(R.id.like_count);
            bookmark = itemView.findViewById(R.id.bookmark);
            likeimg = itemView.findViewById(R.id.like_img);
            viewcount = itemView.findViewById(R.id.view_count);
            itemView.getLayoutParams().height
                    = (int) (((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight() * (35.0f / 100.0f));
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
            // Toast.makeText(context,"test"+getAdapterPosition(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, VideoDetailActivity.class);
           // Bundle bundle = new Bundle();
            intent.putExtra("video_detail",videoContentObjects.get(getAdapterPosition()));
//            intent.putExtra("isLike",videoContentObjects.get(getAdapterPosition()).getIsLiked());
//            intent.putExtra("isBookmark",videoContentObjects.get(getAdapterPosition()).getIsBookmarked());
//            intent.putExtra("likeCount",videoContentObjects.get(getAdapterPosition()).getLikeCount());
//            intent.putExtra("viewCount",videoContentObjects.get(getAdapterPosition()).getViewCount());
//            intent.putExtra("videoId",videoContentObjects.get(getAdapterPosition()).getVideoId());
//            intent.putExtra("HeaderImageId",videoContentObjects.get(getAdapterPosition()).getHeaderImageId());
//            intent.putExtra("ContentId",videoContentObjects.get(getAdapterPosition()).getContentId());
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
