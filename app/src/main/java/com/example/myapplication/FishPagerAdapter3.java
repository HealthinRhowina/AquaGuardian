package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FishPagerAdapter3 extends FragmentStateAdapter {

    public FishPagerAdapter3(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FishFragment31();
            case 1:
                return new FishFragment32();
            default:
                return new FishFragment31();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
