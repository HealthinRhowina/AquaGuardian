package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    TextInputEditText editTextName, editTextEmail, editTextPhone, editTextPassword;
    Button buttonReg;
    ProgressBar progressBar;
    TextView textView;
    FirebaseAuth mAuth;
    DBHelper dbHelper;

    private static final String CHANNEL_ID = "ShareQRChannel";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phone);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.click_here_to_login);

        // Initialize FirebaseAuth and DBHelper
        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DBHelper(this);

        // Request necessary permissions
        requestNotificationPermission();
        requestSMSPermission();

        // Set up button listener for registration
        buttonReg.setOnClickListener(v -> signup());

        // Click listener for "Click here to login" text
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to handle user registration
    private void signup() {
        if (isInputValid()) {
            String name = Objects.requireNonNull(editTextName.getText()).toString().trim();
            String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
            phoneNo = Objects.requireNonNull(editTextPhone.getText()).toString().trim();
            String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
            progressBar.setVisibility(View.VISIBLE);

            // Firebase authentication for user registration
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Save user details to SQLite database
                            boolean inserted = dbHelper.addUser(name, email, phoneNo, password);

                            if (inserted) {
                                Log.d("SignUp", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(Register.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();

                                    // Show notification
                                    createNotificationChannel();
                                    showNotification(email, phoneNo);

                                    // Send SMS
                                    message = "Thank you for registering, " + name + "!";
                                    sendSMSMessage();

                                    // Send Email
                                    sendEmail(email, name);

                                    Intent intent = new Intent(Register.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(Register.this, "Failed to save user to local database", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Registration failed: " + Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to send SMS message
    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // Method to send email
    private void sendEmail(String email, String name) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Registration Successful");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear " + name + ",\n\nThank you for registering!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.d("SendEmail", "Email sent.");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Register.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to show notification
    private void showNotification(String email, String phone) {
        String currentTime = getCurrentTimeString();
        String notificationMessage = "New user registered with email: " + email + " and phone: " + phone + " at " + currentTime;

        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon) // Ensure this icon exists in res/drawable
                .setContentTitle("New User Registration")
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Check for notification permission for Android 13 and above
            return;
        }
        notificationManager.notify(0, builder.build());
    }

    // Method to create notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "User Registration Channel";
            String description = "Channel for new user registration notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Method to request notification permission
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    // Method to request SMS permission
    private void requestSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    // Method to validate user input
    private boolean isInputValid() {
        return !TextUtils.isEmpty(editTextName.getText().toString().trim()) &&
                !TextUtils.isEmpty(editTextEmail.getText().toString().trim()) &&
                !TextUtils.isEmpty(editTextPhone.getText().toString().trim()) &&
                !TextUtils.isEmpty(editTextPassword.getText().toString().trim());
    }

    // Method to get the current time as a formatted string
    private String getCurrentTimeString() {
        java.util.Date date = new java.util.Date();
        return new java.text.SimpleDateFormat("HH:mm:ss").format(date);
    }
}
