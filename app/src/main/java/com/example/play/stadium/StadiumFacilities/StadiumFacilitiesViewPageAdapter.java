package com.example.play.stadium.StadiumFacilities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.play.admin.admin_home.ApprovedPlagroundFragment;
import com.example.play.admin.admin_home.PendingPlaygroundFragment;

public class StadiumFacilitiesViewPageAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"Facilities", "Courts "};

    public StadiumFacilitiesViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new SFacilitiesFragment();

            case 1:
                return new SCourtFragment();
        }
        return  new SFacilitiesFragment();
    }

    @Override
    public int getItemCount() { return title.length; }
}
