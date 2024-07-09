package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private static final String CHANNEL_ID = "fish_notifications";
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private TextView textViewAquaguard;
    private ArrayList<String> uploadedImages; // Track uploaded image URIs or paths

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        // Initialize views
        imageButton = findViewById(R.id.imageButtonn);
        imageButton2 = findViewById(R.id.imageButtonn2);
        textViewAquaguard = findViewById(R.id.aquaguardText);

        // Initialize ArrayList for uploaded images
        uploadedImages = new ArrayList<>();

        // Set click listeners for ImageButtons
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FishActivity.class);
                startActivity(intent);
                sendLocalNotification(); // Trigger notification on button click
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ShopList.class);
                startActivity(intent);
            }
        });

        // Add listener for imageButtonn3 (new upload image button)
        ImageButton imageButton3 = findViewById(R.id.imageButtonn3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NextActivity.class);
                startActivityForResult(intent, 1); // Request code can be any integer
            }
        });

        // Set text with two colors
        setDualColorText();

        // Create notification channel
        createNotificationChannel();
    }

    // Method to handle result from NextActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Add the uploaded image URI or path to the ArrayList
            String imagePath = data.getStringExtra("imagePath"); // Replace with your key
            if (imagePath != null) {
                uploadedImages.add(imagePath);
            }
        }
    }

    private void setDualColorText() {
        String text = "AQUAGUARDIAN";
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewAquaguard.setText(spannable);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Fish Notifications";
            String description = "Channel for fish notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendLocalNotification() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.noti)
                .setContentTitle("Local Notification")
                .setContentText("This is a local notification from the Home activity.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
