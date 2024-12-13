package com.example.steptothebeat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageButton settingsButton, startWorkoutButton, achievementsButton;
    private ImageView profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity); // Set the layout to activity_home.xml
        EdgeToEdge.enable(this);

        init();
    }

    void init() {
        // Initialize the 4 buttons
        profileButton = findViewById(R.id.profileImage);
        settingsButton = findViewById(R.id.settingsButton);
        startWorkoutButton = findViewById(R.id.startWorkoutButton);
        achievementsButton = findViewById(R.id.achievementsButton);

        Activity currentA = this;


        // Navigate to correct page on button click
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Profile page
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

                // Set an exit transition
                View profileView = findViewById(R.id.profileImage);
                if (profileView != null) {
                    //getWindow().setSharedElementExitTransition(new ChangeImageTransform());
                    //getWindow().setSharedElementEnterTransition(new ChangeImageTransform());
                    //getWindow().setExitTransition(new ChangeImageTransform());
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(currentA, profileView, "profile");
                    startActivity(intent, options.toBundle());
                }
                else {
                    getWindow().setExitTransition(new Explode());
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(currentA).toBundle());
                }
                //startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Settings page
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                Transition t = new AutoTransition();

                startActivity(intent);
            }
        });

        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to ChoosePace Page
                Intent intent = new Intent(HomeActivity.this, ChoosePaceActivity.class);
                View startView = findViewById(R.id.startWorkoutButton);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(currentA, startView, "start");
                startActivity(intent, options.toBundle());
                //startActivity(intent);
            }
        });

        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Achievements page
                Intent intent = new Intent(HomeActivity.this, AchievementsActivity.class);
                View achievementsView = findViewById(R.id.achievementsButton);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(currentA, achievementsView, "achievements");
                startActivity(intent, options.toBundle());
                //startActivity(intent);
            }
        });
    }
}
