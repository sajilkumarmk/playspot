package com.example.play.stadium.StadiumBooking;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StadiumBookingViewPageAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"Current", "History"};

    public StadiumBookingViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new StadiumCurrentBookingFragment();

            case 1:
                return new StadiumHistoryBookingFragment();
        }
        return  new StadiumCurrentBookingFragment();
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
