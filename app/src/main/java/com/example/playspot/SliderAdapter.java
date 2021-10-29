package com.example.playspot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    ImageView introImageView;
    TextView introHeading, introDescription;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public  int[] slider_images = {
            R.drawable.booking,
            R.drawable.tracking,
            R.drawable.account,
            R.drawable.health
    };
    public String[] slide_headings = {
            "Booking",
            "Activity Tracking",
            "Account Monitoring",
            "Health Monitoring"
    };
    public String[] slide_descs = {
            "Book your favorite playing spot",
            "Track your daily gaming activity",
            "Manage your accounting details efficiently",
            "Monitor your health and activity"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        introImageView = view.findViewById(R.id.introImageView);
        introHeading = view.findViewById(R.id.introHeading);
        introDescription = view.findViewById(R.id.introDescription);

        introImageView.setImageResource(slider_images[position]);
        introHeading.setText(slide_headings[position]);
        introDescription.setText(slide_descs[position]);

        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}