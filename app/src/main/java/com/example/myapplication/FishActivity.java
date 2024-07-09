package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class FishActivity extends AppCompatActivity {

    private List<Button> buttons = new ArrayList<>();
    private List<Button> allButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);

        SearchView searchView = findViewById(R.id.search_view);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);

        button1.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 1 clicked");
            navigateToActivity(AFish1.class);
        });
        button2.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 2 clicked");
            navigateToActivity(AFish2.class);
        });
        button3.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 3 clicked");
            navigateToActivity(AFish3.class);
        });
        button4.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 4 clicked");
            navigateToActivity(AFish4.class);
        });
        button5.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 5 clicked");
            navigateToActivity(AFish5.class);
        });
        button6.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 6 clicked");
            navigateToActivity(AFish6.class);
        });
        button7.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 7 clicked");
            navigateToActivity(AFish7.class);
        });
        button8.setOnClickListener(v -> {
            Log.d("FishActivity", "Button 8 clicked");
            navigateToActivity(AFish8.class);
        });

        allButtons.add(button1);
        allButtons.add(button2);
        allButtons.add(button3);
        allButtons.add(button4);
        allButtons.add(button5);
        allButtons.add(button6);
        allButtons.add(button7);
        allButtons.add(button8);

        buttons.addAll(allButtons);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterButtons(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterButtons(newText);
                return false;
            }
        });
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void filterButtons(String query) {
        query = query.toLowerCase();
        buttons.clear();
        for (Button button : allButtons) {
            // Assuming the text of each Button is set to the fish name
            String buttonText = button.getText().toString().toLowerCase();
            if (buttonText.contains(query)) {
                button.setVisibility(View.VISIBLE);
                buttons.add(button);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FishActivity.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
