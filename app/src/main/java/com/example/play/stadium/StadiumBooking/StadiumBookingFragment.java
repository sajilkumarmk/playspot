package com.example.play.stadium.StadiumBooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.play.databinding.FragmentStadiumBookingBinding;
public class StadiumBookingFragment extends Fragment {


    private FragmentStadiumBookingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentStadiumBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBooking;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}