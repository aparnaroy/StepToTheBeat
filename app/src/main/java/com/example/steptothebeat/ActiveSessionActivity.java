package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ActiveSessionActivity extends AppCompatActivity {
    private ImageButton endSessionButton, pauseButton, startWorkoutButton, achievementsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_session_activity); // Set the layout to activity_home.xml
        EdgeToEdge.enable(this);

        // Get the activity type passed from the ChoosePaceActivity
        String pace = getIntent().getStringExtra("pace");
        // Display the activity type in a TextView (just for example)
        TextView selectedActivityTextView = findViewById(R.id.exercise);
        if (pace != null) {
            selectedActivityTextView.setText("Exercise: " + pace);
        }

        init();
    }

    void init() {
        // Initialize the 4 buttons
        endSessionButton = findViewById(R.id.end_session);
        pauseButton = findViewById(R.id.pause_button);

        // Navigate to correct page on button click
        endSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Session Summary page
                Intent intent = new Intent(ActiveSessionActivity.this, SessionSummaryActivity.class);
                startActivity(intent);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Settings page
                Intent intent = new Intent(ActiveSessionActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
