package com.example.playspot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RegistrationViewPageAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"User", "Playground"};

    public RegistrationViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserRegistrationFragment();
            case 1:
                return new PlaygroundRegistrationFragment();
        }
        return  new UserRegistrationFragment();
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
