package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FishPagerAdapter8 extends FragmentStateAdapter {

    public FishPagerAdapter8(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FishFragment81();
            case 1:
                return new FishFragment82();
            default:
                return new FishFragment81();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
