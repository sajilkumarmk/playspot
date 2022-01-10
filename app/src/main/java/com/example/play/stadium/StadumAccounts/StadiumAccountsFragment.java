package com.example.play.stadium.StadumAccounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.play.databinding.FragmentStadiumAccountsBinding;
import com.example.play.stadium.StadiumFacilities.StadiumFacilitiesViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
//import com.example.twobottomnav.databinding.FragmentHomeBinding;

public class StadiumAccountsFragment extends Fragment {


    private FragmentStadiumAccountsBinding binding;
    StadiumAccountsViewPageAdapter stadiumAccountsViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
//    TextView totalExpense;
    private String[] title = new String[]{"Expences", "Payments"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStadiumAccountsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager2 = binding.sAccountsViewPage;
        tabLayout = binding.sAccountsTab;
        stadiumAccountsViewPageAdapter = new StadiumAccountsViewPageAdapter(getActivity());

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