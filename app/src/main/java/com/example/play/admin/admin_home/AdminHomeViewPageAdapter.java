package com.example.play.admin.admin_home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdminHomeViewPageAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"Approved", "Pending"};

    public AdminHomeViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ApprovedPlagroundFragment();

            case 1:
                return new PendingPlaygroundFragment();
        }
        return  new ApprovedPlagroundFragment();

    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
