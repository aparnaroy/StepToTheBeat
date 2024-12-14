package com.example.steptothebeat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SessionSummaryActivity extends BaseActivity {

    private View achievementBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_session);
        addMenuBarSpace(R.id.session_summary);
        setupToolbar(R.id.menubar, true);



        // Get session duration
        long duration = getSharedPreferences("session", MODE_PRIVATE)
                .getLong("session_duration", 0);

        // Convert to HH:MM:SS
        int seconds = (int) (duration / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        // Display duration
        TextView durationText = findViewById(R.id.duration_text);
        durationText.setText(String.format("Duration: %02d:%02d:%02d", hours, minutes, seconds));

        // Check if banner has been shown before for hour+ achievement
        boolean bannerShownBefore1Hour = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("hour_session_achievement", false);

        // Check if session was 1 hour or longer
        if (duration >= 3600000 && !bannerShownBefore1Hour) { // 3600000 milliseconds = 1 hour
            // Save the achievement state
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("hour_session_achievement", true)
                    .apply();

            //Show achievement banner
            showAchievementBanner();

            // Mark the banner as shown
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("run_achievement_banner_shown", true)
                    .apply();
        }
        // Add the banner view
        ViewGroup rootView = findViewById(android.R.id.content);
        achievementBanner = getLayoutInflater().inflate(R.layout.achievement_banner, rootView, false);
        rootView.addView(achievementBanner);

        // Initially hide the banner off-screen
        achievementBanner.setTranslationY(-achievementBanner.getHeight());
        achievementBanner.setVisibility(View.GONE);

        // Update consecutive days and check for achievement
        updateConsecutiveDays();

        // Check both achievement conditions
        boolean walkAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_unlocked", false);

        // Set walkAchievementUnlockedComplete to true when reaching this achievement
        getSharedPreferences("achievements", MODE_PRIVATE)
                .edit()
                .putBoolean("walk_achievement_unlocked_complete", true)
                .apply();

        boolean walkAchievementUnlockedComplete = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_unlocked_complete", false);

        // Check if banner has been shown before for walk
        boolean bannerShownBeforeWalk = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("walk_achievement_banner_shown", false);

        // Check both achievement conditions
        boolean runAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("run_achievement_unlocked", false);

        // Set walkAchievementUnlockedComplete to true when reaching this achievement
        getSharedPreferences("achievements", MODE_PRIVATE)
                .edit()
                .putBoolean("run_achievement_unlocked_complete", true)
                .apply();

        boolean runAchievementUnlockedComplete = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("run_achievement_unlocked_complete", false);

        // Check if banner has been shown before for run
        boolean bannerShownBeforeRun = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("run_achievement_banner_shown", false);

        // Check power walk achievement conditions
        boolean powerWalkAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("power_walk_achievement_unlocked", false);

        boolean powerWalkAchievementComplete = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("power_walk_achievement_unlocked_complete", true);

        boolean bannerShownBefore = getSharedPreferences("achievements", MODE_PRIVATE)
                .getBoolean("power_walk_banner_shown", false);


        if (walkAchievementUnlocked && walkAchievementUnlockedComplete && !bannerShownBeforeWalk) {
            // Show the achievement banner
            showAchievementBanner();

            // Mark the banner as shown
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("walk_achievement_banner_shown", true)
                    .apply();
        }
        else if (runAchievementUnlocked && runAchievementUnlockedComplete && !bannerShownBeforeRun) {
            // Show the achievement banner
            showAchievementBanner();

            // Mark the banner as shown
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("run_achievement_banner_shown", true)
                    .apply();
        }
        else if (powerWalkAchievementUnlocked && powerWalkAchievementComplete && !bannerShownBefore) {
            // Show achievement banner
            showAchievementBanner();

            // Mark banner as shown
            getSharedPreferences("achievements", MODE_PRIVATE)
                    .edit()
                    .putBoolean("power_walk_banner_shown", true)
                    .apply();
        }


    }

    private void updateConsecutiveDays() {
        SharedPreferences prefs = getSharedPreferences("achievements", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Get today's date and format it
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Get the last exercise date
        String lastDate = prefs.getString("last_exercise_date", "");

        if (!lastDate.equals(today)) {  // Only update if not already logged today
            // Get current streak
            int currentStreak = prefs.getInt("exercise_streak", 0);

            if (isConsecutiveDay(lastDate)) {
                currentStreak++;
            } else {
                currentStreak = 1; // Reset streak if not consecutive
            }

            // Save new streak and date
            editor.putInt("exercise_streak", currentStreak);
            editor.putString("last_exercise_date", today);

            // Check if 14-day achievement unlocked
            if (currentStreak >= 14 && !prefs.getBoolean("fourteen_day_achievement", false)) {
                editor.putBoolean("fourteen_day_achievement", true);
                editor.apply();

                // Update achievement text
                LinearLayout achievement3 = findViewById(R.id.achievement3);
                TextView achievementText = (TextView) achievement3.getChildAt(0);
                achievementText.setText("Exercise for 14 days in a row!\nStatus: Unlocked!");

                // Show banner only if this is the first time unlocking
                if (!prefs.getBoolean("fourteen_day_banner_shown", false)) {
                    showAchievementBanner();
                    editor.putBoolean("fourteen_day_banner_shown", true);
                    editor.apply();
                }
            }

            editor.apply();
        }
    }

    private boolean isConsecutiveDay(String lastDate) {
        if (lastDate.isEmpty()) return true;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date last = sdf.parse(lastDate);
            Date today = new Date();

            // Calculate difference in days
            long diffInMillies = today.getTime() - last.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diffInDays == 1;
        } catch (ParseException e) {
            return false;
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
