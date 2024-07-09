package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FishFragment41 extends Fragment {
    private AnimationDrawable isAnimation;
    private ImageView img;
    private TextView fishNameTextView;
    private String fishName = "Angelfish (Pterophyllum scalare)";
    private int index = 0;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish41, container, false);

        img = view.findViewById(R.id.img);
        fishNameTextView = view.findViewById(R.id.fish_name);

        img.setImageResource(R.drawable.anif4);
        isAnimation = (AnimationDrawable) img.getDrawable();
        isAnimation.start();

        startTypingAnimation();

        return view;
    }

    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < fishName.length()) {
                    fishNameTextView.setText(fishName.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100); // Delay for typing effect (100 ms)
                }
            }
        }, 100); // Initial delay
    }
}
