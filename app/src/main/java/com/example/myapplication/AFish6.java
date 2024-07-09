package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class AFish6 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FishPagerAdapter6 fishPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afish6);

        viewPager = findViewById(R.id.viewPager);
        fishPagerAdapter = new FishPagerAdapter6(this);
        viewPager.setAdapter(fishPagerAdapter);
    }
}
