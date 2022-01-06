package com.example.playspot.admin.admin_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.playspot.databinding.FragmentAdminhomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminhomeBinding binding;
    AdminHomeViewPageAdapter adminhomeViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] title = new String[]{"Approved", "Pending"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminhomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.viewPager;
        tabLayout = binding.tabLayout;
        adminhomeViewPageAdapter = new AdminHomeViewPageAdapter(getActivity());

        viewPager2.setAdapter(adminhomeViewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(title[position]))).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}