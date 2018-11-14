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
