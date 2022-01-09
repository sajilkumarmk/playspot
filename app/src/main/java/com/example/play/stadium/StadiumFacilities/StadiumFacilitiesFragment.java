package com.example.play.stadium.StadiumFacilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.play.admin.admin_home.AdminHomeViewPageAdapter;
import com.example.play.databinding.FragmentAdminhomeBinding;
import com.example.play.databinding.FragmentStadiumFacilitiesBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StadiumFacilitiesFragment extends Fragment {

    private FragmentStadiumFacilitiesBinding binding;
    StadiumFacilitiesViewPageAdapter stadiumFacilitiesViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] title = new String[]{"Facilities", "Courts"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStadiumFacilitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.sfacilitiesViewPage;
        tabLayout = binding.sfacilitiesTab;
        stadiumFacilitiesViewPageAdapter = new StadiumFacilitiesViewPageAdapter(getActivity());

        viewPager2.setAdapter(stadiumFacilitiesViewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(title[position]))).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}