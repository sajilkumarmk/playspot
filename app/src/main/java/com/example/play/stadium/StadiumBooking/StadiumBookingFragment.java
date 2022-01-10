package com.example.play.stadium.StadiumBooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.play.databinding.FragmentStadiumBookingBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StadiumBookingFragment extends Fragment {

    private FragmentStadiumBookingBinding binding;
    StadiumBookingViewPageAdapter stadiumAccountsViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] title = new String[]{"Current", "History"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentStadiumBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.sBookingViewPage;
        tabLayout = binding.sBookingTab;
        stadiumAccountsViewPageAdapter = new StadiumBookingViewPageAdapter(getActivity());

        viewPager2.setAdapter(stadiumAccountsViewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(title[position]))).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}