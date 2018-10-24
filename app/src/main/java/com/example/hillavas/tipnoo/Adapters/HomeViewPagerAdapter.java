package com.example.hillavas.tipnoo.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hillavas.tipnoo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeViewPagerAdapter extends PagerAdapter {

    Context c;
    TextView textView;
    private List<String> _imagePaths;
    private List<String> _imgnames;
    private LayoutInflater inflater;

    public HomeViewPagerAdapter(Context c, List<String> imagePaths, List<String> imgnames) {
        this._imagePaths = imagePaths;
        this._imgnames=imgnames;
        this.c = c;
    }


    @Override
    public int getCount() {

        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.home_pager_item, container,
                false);
        textView=viewLayout.findViewById(R.id.textview_home_viewpager);
        imgDisplay =  viewLayout.findViewById(R.id.image_home_viewpager);
        Picasso.with(c).load(_imagePaths.get(position)).into(imgDisplay);
        (container).addView(viewLayout);
        textView.setText(_imgnames.get(position));
                     return viewLayout;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);
    }


}

