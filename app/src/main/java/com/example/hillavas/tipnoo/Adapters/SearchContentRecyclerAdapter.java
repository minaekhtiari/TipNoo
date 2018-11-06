package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hillavas.tipnoo.Models.VideoContentObject;
import com.example.hillavas.tipnoo.R;

import java.util.ArrayList;

public class SearchContentRecyclerAdapter extends RecyclerView.Adapter<SearchContentRecyclerAdapter.ViewHolder> {
    private ArrayList<VideoContentObject> videoContentObjects;
    Context context;
    private HomeContentRecyclerAdapter.ItemClickListener mClickListener;



    public SearchContentRecyclerAdapter(Context context, ArrayList<VideoContentObject> videoContentObjects) {
        this.context=context;
        this.videoContentObjects = videoContentObjects;
    }

    @NonNull
    @Override
    public SearchContentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_rcyclersearch,viewGroup, false);
        return new ViewHolder(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchContentRecyclerAdapter.ViewHolder holder, int position) {
        VideoContentObject videoContentObjectPositon = videoContentObjects.get(position);
        holder.title.setText(videoContentObjectPositon.getSubject());
    }

    @Override
    public int getItemCount() {
        return videoContentObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           title=itemView.findViewById(R.id.title);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

          // int tagId=videoContentObjects.get(getAdapterPosition()).getValue();
           // Toast.makeText(context,tagId+"",Toast.LENGTH_LONG).show();
        }
    }

    void setClickListener(HomeContentRecyclerAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}