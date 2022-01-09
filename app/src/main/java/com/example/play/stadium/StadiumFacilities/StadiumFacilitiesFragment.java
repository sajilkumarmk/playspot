package com.example.play.stadium.StadiumFacilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.play.databinding.FragmentStadiumFacilitiesBinding;
//import com.example.twobottomnav.databinding.FragmentNotificationsBinding;

public class StadiumFacilitiesFragment extends Fragment {


    private FragmentStadiumFacilitiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStadiumFacilitiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFacilities;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}