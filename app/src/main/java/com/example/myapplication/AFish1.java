package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class AFish1 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FishPagerAdapter fishPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afish1);

        viewPager = findViewById(R.id.viewPager);
        fishPagerAdapter = new FishPagerAdapter(this);
        viewPager.setAdapter(fishPagerAdapter);
    }
}
