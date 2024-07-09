package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView userDetails;
    AutoLogoutManager autoLogoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AutoLogoutManager
        autoLogoutManager = new AutoLogoutManager(this);
        getLifecycle().addObserver(autoLogoutManager);

        userDetails = findViewById(R.id.user_details);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // User not authenticated, navigate to Login
            navigateToLogin();
        } else {
            userDetails.setText(currentUser.getEmail());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoLogoutManager.resetLogoutTimer(); // Reset timer on activity resume
    }

    private void navigateToLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
