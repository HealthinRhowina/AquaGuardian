package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FishPagerAdapter extends FragmentStateAdapter {

    public FishPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fish11Fragment();
            case 1:
                return new Fish12Fragment();
            default:
                return new Fish11Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
