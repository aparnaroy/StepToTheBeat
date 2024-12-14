package com.example.steptothebeat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

public class SessionSummaryActivity extends BaseActivity {

    private View achievementBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_session);
        addMenuBarSpace(R.id.session_summary);
        setupToolbar(R.id.menubar, true);

        // Add the banner view
        ViewGroup rootView = findViewById(android.R.id.content);
        achievementBanner = getLayoutInflater().inflate(R.layout.achievement_banner, rootView, false);
        rootView.addView(achievementBanner);

        // Initially hide the banner off-screen
        achievementBanner.setTranslationY(-achievementBanner.getHeight());
        achievementBanner.setVisibility(View.GONE);

        // Check both achievement conditions
        boolean walkAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_unlocked", false);

        // Set walkAchievementUnlockedComplete to true when reaching this activity
        getSharedPreferences("achievements", MODE_PRIVATE)
                .edit()
                .putBoolean("walk_achievement_unlocked_complete", true)
                .apply();

        boolean walkAchievementUnlockedComplete = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_unlocked_complete", false);

        // Check if banner has been shown before
        boolean bannerShownBefore = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_banner_shown", false);

        if (walkAchievementUnlocked && walkAchievementUnlockedComplete && !bannerShownBefore) {
            // Show the achievement banner
            showAchievementBanner();

            // Mark the banner as shown
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("walk_achievement_banner_shown", true)
                    .apply();
        }
    }

    private void showAchievementBanner() {
        achievementBanner.setVisibility(View.VISIBLE);
        achievementBanner.animate()
                .translationY(0)
                .setDuration(800)  // Slide down animation duration
                .withEndAction(() -> {
                    // Wait 2 seconds then slide up
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        achievementBanner.animate()
                                .translationY(-achievementBanner.getHeight())
                                .setDuration(800)  // Slide up animation duration
                                .withEndAction(() -> achievementBanner.setVisibility(View.GONE))
                                .start();
                    }, 2000);  // Banner display duration
                })
                .start();
    }
}
