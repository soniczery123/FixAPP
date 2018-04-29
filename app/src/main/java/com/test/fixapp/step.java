package com.test.fixapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.fixapp.model.slideAdapter;

public class step extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private slideAdapter mSlideAdapter;
    private Button buttonNext,buttonPrev;
    private TextView[] mDot;
    int mCurrentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        getSupportActionBar().hide();

        buttonNext = (Button)findViewById(R.id.buttonNext);
        buttonPrev = (Button)findViewById(R.id.buttonPrev);
        buttonPrev.setBackgroundColor(Color.TRANSPARENT);
        buttonNext.setBackgroundColor(Color.TRANSPARENT);

        viewPager = (ViewPager) findViewById(R.id.slide_layout);
        dotLayout = (LinearLayout)findViewById(R.id.dotLayout);

        mSlideAdapter = new slideAdapter(   this);
        viewPager.setAdapter(mSlideAdapter);
        addDotIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonNext.getText().toString().equals("FINISH")){
                    Intent intent = new Intent(step.this,login.class);
                    startActivity(intent);
                }else{
                    viewPager.setCurrentItem(mCurrentPage+1);
                }
            }
        });
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }
    public void addDotIndicator(int position){
        mDot = new TextView[3];
        dotLayout.removeAllViews();
        for(int i = 0 ; i < mDot.length ; i++){
            mDot[i] = new TextView(this);
            mDot[i].setText(Html.fromHtml("&#8226;"));
            mDot[i].setTextSize(35);
            mDot[i].setTextColor(getResources().getColor(R.color.colorAccent));
            dotLayout.addView(mDot[i]);
        }
        if(mDot.length > 0){
            mDot[position].setTextColor(getResources().getColor(R.color.colorGreen));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotIndicator(position);
            mCurrentPage = position;
            if(mCurrentPage == 0){
                buttonPrev.setEnabled(false);
                buttonNext.setEnabled(true);
                buttonPrev.setText("");
                buttonNext.setText("NEXT");
            }else if(mCurrentPage == mDot.length-1){
                buttonPrev.setEnabled(true);
                buttonNext.setEnabled(true);
                buttonPrev.setText("PREV");
                buttonNext.setText("FINISH");
            }else{
                buttonPrev.setEnabled(true);
                buttonNext.setEnabled(true);
                buttonPrev.setText("PREV");
                buttonNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
