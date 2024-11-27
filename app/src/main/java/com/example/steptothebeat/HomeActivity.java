package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // Define the buttons
    private ImageButton profileButton, settingsButton, startWorkoutButton, achievementsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Set the layout to activity_home.xml
        EdgeToEdge.enable(this);

        // Initialize the buttons
//        profileButton = findViewById(R.id.profileButton);
//        settingsButton = findViewById(R.id.settingsButton);
//        startWorkoutButton = findViewById(R.id.startWorkoutButton);
//        achievementsButton = findViewById(R.id.achievementsButton);

        // Set up listeners for each button to navigate to the appropriate activity
//        profileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Profile Activity
//                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Settings Activity
//                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start StartWorkout Activity
//                Intent intent = new Intent(HomeActivity.this, StartWorkoutActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        achievementsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start Achievements Activity
//                Intent intent = new Intent(HomeActivity.this, AchievementsActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
