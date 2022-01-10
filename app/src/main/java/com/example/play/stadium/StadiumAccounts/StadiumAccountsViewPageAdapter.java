package com.example.play.stadium.StadiumAccounts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StadiumAccountsViewPageAdapter extends FragmentStateAdapter {

    private String[] title = new String[]{"Expences", "Payments"};

    public StadiumAccountsViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new SExpenseFragment();

            case 1:
                return new SPaymentFragment();
        }
        return  new SExpenseFragment();
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
