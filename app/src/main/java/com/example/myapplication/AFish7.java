package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class AFish7 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FishPagerAdapter7 fishPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afish7);

        viewPager = findViewById(R.id.viewPager);
        fishPagerAdapter = new FishPagerAdapter7(this);
        viewPager.setAdapter(fishPagerAdapter);
    }
}
