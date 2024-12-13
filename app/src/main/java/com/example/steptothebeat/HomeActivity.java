package com.example.steptothebeat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
    private AnimatorSet profileButtonAnimatorSet, settingsButtonAnimatorSet, startWorkoutButtonAnimatorSet, achievementsButtonAnimatorSet;

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

        // Start button floating animations
        startFloatingAnimation(profileButton, 40f, 60f, 4000, 3500);
        startFloatingAnimation(settingsButton, 40f, 65f, 2800, 4300);
        startFloatingAnimation(startWorkoutButton, 40f, 65f, 3500, 4000);
        startFloatingAnimation(achievementsButton, 40f, 60f, 4200, 3700);


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

                View settingsView = findViewById(R.id.settingsButton);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(currentA, settingsView, "settings");
                startActivity(intent, options.toBundle());
                //startActivity(intent);
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

    // Floating animations for 4 homepage buttons
    private void startFloatingAnimation(View view, float rangeX, float rangeY, long durationX, long durationY) {
        // Create ObjectAnimators for X and Y translation
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", -rangeX, rangeX);
        animatorX.setRepeatMode(ObjectAnimator.REVERSE);
        animatorX.setRepeatCount(ObjectAnimator.INFINITE);
        animatorX.setDuration(durationX);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", -rangeY, rangeY);
        animatorY.setRepeatMode(ObjectAnimator.REVERSE);
        animatorY.setRepeatCount(ObjectAnimator.INFINITE);
        animatorY.setDuration(durationY);

        // Combine animations into an AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();

        // Assign the AnimatorSet to the correct variable
        if (view == profileButton) {
            profileButtonAnimatorSet = animatorSet;
        } else if (view == settingsButton) {
            settingsButtonAnimatorSet = animatorSet;
        } else if (view == startWorkoutButton) {
            startWorkoutButtonAnimatorSet = animatorSet;
        } else if (view == achievementsButton) {
            achievementsButtonAnimatorSet = animatorSet;
        }
    }

    // Pause button animations when a button is clicked
    @Override
    protected void onPause() {
        super.onPause();
        // Pause all animations
        if (profileButtonAnimatorSet != null) {
            profileButtonAnimatorSet.pause();
        }
        if (settingsButtonAnimatorSet != null) {
            settingsButtonAnimatorSet.pause();
        }
        if (startWorkoutButtonAnimatorSet != null) {
            startWorkoutButtonAnimatorSet.pause();
        }
        if (achievementsButtonAnimatorSet != null) {
            achievementsButtonAnimatorSet.pause();
        }
    }

    // Resume all button animations when we come back to this homepage
    @Override
    protected void onResume() {
        super.onResume();
        // Resume all animations
        if (profileButtonAnimatorSet != null) {
            profileButtonAnimatorSet.resume();
        }
        if (settingsButtonAnimatorSet != null) {
            settingsButtonAnimatorSet.resume();
        }
        if (startWorkoutButtonAnimatorSet != null) {
            startWorkoutButtonAnimatorSet.resume();
        }
        if (achievementsButtonAnimatorSet != null) {
            achievementsButtonAnimatorSet.resume();
        }
    }
}
