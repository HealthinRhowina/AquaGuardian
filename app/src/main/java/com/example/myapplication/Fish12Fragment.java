package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fish12Fragment extends Fragment {

    private VideoView videoView;
    private TextView videoDescription;
    private Button playButton;
    private Button pauseButton;
    private SeekBar videoSeekBar;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish12, container, false);

        videoView = view.findViewById(R.id.videoView);
        videoDescription = view.findViewById(R.id.video_description);
        playButton = view.findViewById(R.id.playButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        videoSeekBar = view.findViewById(R.id.videoSeekBar);

        // Set video URI
        Uri videoUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.f1);
        videoView.setVideoURI(videoUri);

        // Set video description text

        // Set up play button
        playButton.setOnClickListener(v -> {
            videoView.start();
            updateSeekBar();
        });

        // Set up pause button
        pauseButton.setOnClickListener(v -> videoView.pause());

        // Set up SeekBar
        videoView.setOnPreparedListener(mp -> {
            videoSeekBar.setMax(videoView.getDuration());
            videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        videoView.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        });

        return view;
    }

    private void updateSeekBar() {
        if (videoView.isPlaying()) {
            videoSeekBar.setProgress(videoView.getCurrentPosition());
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
