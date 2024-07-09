package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FishPagerAdapter7 extends FragmentStateAdapter {

    public FishPagerAdapter7(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FishFragment71();
            case 1:
                return new FishFragment72();
            default:
                return new FishFragment71();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
