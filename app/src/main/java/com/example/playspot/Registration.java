package com.example.playspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Registration extends AppCompatActivity {

    RegistrationViewPageAdapter registrationViewPageAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] title = new String[]{"User", "Playground"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout =findViewById(R.id.tabLayout);
        registrationViewPageAdapter = new RegistrationViewPageAdapter(this);

        viewPager2.setAdapter(registrationViewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(title[position]))).attach();
    }
}