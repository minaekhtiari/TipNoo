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

import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.R;
import com.example.hillavas.tipnoo.VideoDetailActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

//import com.example.hillavas.bottomnavigationview.R;


public class HomeContentRecyclerAdapter extends RecyclerView.Adapter<HomeContentRecyclerAdapter.ViewHolder> {
    private ArrayList<VideoContentObject> videoContentObjects;
    Context context;

    private ItemClickListener mClickListener;

    public HomeContentRecyclerAdapter(Context context, ArrayList<VideoContentObject> videoContentObjects){
        this.context=context;
        this.videoContentObjects = videoContentObjects;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v=  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_rcyclehomefragment,viewGroup, false);
        return new ViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        VideoContentObject videoContentObjectPositon = videoContentObjects.get(position);
        viewHolder.title.setText(videoContentObjectPositon.getSubject());

       Picasso.with(context).load("http://79.175.138.77:7091/file/getfile?FileType=image&fileid="+ videoContentObjectPositon.getHeaderImageId()).into(new Target(){

           @Override
           public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
               viewHolder.imageView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
           }

           @Override
           public void onBitmapFailed(final Drawable errorDrawable) {
               Log.d("TAG", "FAILED");
           }

           @Override
           public void onPrepareLoad(final Drawable placeHolderDrawable) {
               Log.d("TAG", "Prepare Load");
           }
       });



    }

    @Override
    public int getItemCount() {
       return videoContentObjects.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.content_title_home_fragment);
            imageView=itemView.findViewById(R.id.image_view_home_fragment);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
