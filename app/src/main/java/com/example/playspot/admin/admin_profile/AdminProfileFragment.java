package com.example.playspot.admin.admin_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.playspot.Login;
import com.example.playspot.SessionManager;
import com.example.playspot.databinding.FragmentAdminprofileBinding;

public class AdminProfileFragment extends Fragment {


    private FragmentAdminprofileBinding binding;
    EditText email,password;
    ImageButton logout;
    AppCompatButton update;
    String adminEmail,adminPassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminprofileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        email = binding.email;
        password = binding.password;
        logout = binding.logout;
        update = binding.update;

        adminEmail = new SessionManager(getActivity()).getUserDetails().get("email");
        adminPassword = new SessionManager(getActivity()).getUserDetails().get("password");

        email.setText(adminEmail);
        password.setText(adminPassword);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SessionManager(getActivity()).logoutUser();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}