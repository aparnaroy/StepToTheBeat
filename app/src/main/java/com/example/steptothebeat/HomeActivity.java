package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageButton profileButton, settingsButton, startWorkoutButton, achievementsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity); // Set the layout to activity_home.xml
        EdgeToEdge.enable(this);

        init();
    }

    void init() {
        // Initialize the 4 buttons
        profileButton = findViewById(R.id.profileButton);
        settingsButton = findViewById(R.id.settingsButton);
        startWorkoutButton = findViewById(R.id.startWorkoutButton);
        achievementsButton = findViewById(R.id.achievementsButton);

        Scene homeScene;
        Scene profileScene;
        Scene settingsScene;
        Scene choosePaceScene;
        Scene achievementScene;

        // Create the scene root for the scenes in this app.
        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.scene_root);

        // Create the scenes.
        homeScene = Scene.getSceneForLayout(sceneRoot, R.layout.home_activity, this);
        profileScene = Scene.getSceneForLayout(sceneRoot, R.layout.profile_activity, this);
        settingsScene = Scene.getSceneForLayout(sceneRoot, R.layout.settings_activity, this);
        choosePaceScene = Scene.getSceneForLayout(sceneRoot, R.layout.choose_pace_activity, this);
        achievementScene = Scene.getSceneForLayout(sceneRoot, R.layout.achievements, this);

        Transition t = new AutoTransition();

        // Navigate to correct page on button click
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Profile page
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                //TransitionManager.go(profileScene, t);
                startActivity(intent);
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
                startActivity(intent);
            }
        });

        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Achievements page
                Intent intent = new Intent(HomeActivity.this, AchievementsActivity.class);
                startActivity(intent);
            }
        });
    }
}
