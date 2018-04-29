package com.test.fixapp.model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.fixapp.R;

/**
 * Created by MSI-GL62 on 27/4/2561.
 */

public class slideAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater layoutInflater;
    public String[] slide_Headings = {
        "A",
        "B",
        "C"
    };
    public String[] slide_Details = {
            "aaaa",
            "bbbb",
            "cccc"
    };
    public slideAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getCount(){
        return slide_Headings.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == (RelativeLayout) object;
    }
    @Override
    public Object instantiateItem(ViewGroup container , int position){
        layoutInflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        TextView textViewHeading = (TextView)view.findViewById(R.id.textViewHeading);
        TextView textViewDetail = (TextView)view.findViewById(R.id.textViewDetail);

        textViewHeading.setText(slide_Headings[position]);
        textViewDetail.setText(slide_Details[position]);

        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}
