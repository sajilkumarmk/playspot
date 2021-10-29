package com.example.playspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroSlider extends AppCompatActivity {

    private ViewPager introViewPager;
    private LinearLayout introLinear;

    private TextView[] dots;

    private SliderAdapter sliderAdapter;

    private AppCompatButton skip,next;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        introViewPager = findViewById(R.id.introViewPager);
        introLinear = findViewById(R.id.introLinear);
        skip = findViewById(R.id.introSkipbtn);
        next = findViewById(R.id.introNextntn);

        sliderAdapter = new SliderAdapter(this);
        introViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        introViewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage != dots.length -1){
                    introViewPager.setCurrentItem(currentPage + 1);
                }else{
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void addDotsIndicator(int position){
        dots = new TextView[4];
        introLinear.removeAllViews();

        for (int i =0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white_300));

            introLinear.addView(dots[i]);

        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.white_100));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            currentPage = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}

