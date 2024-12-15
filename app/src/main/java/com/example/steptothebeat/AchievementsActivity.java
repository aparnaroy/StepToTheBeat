package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AchievementsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements); //Set layout to achievements.xml page
        setupToolbar(R.id.menubar,true);
        addMenuBarSpace(R.id.achievements);

        boolean walkAchievementUnlocked = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("walk_achievement_unlocked",false); //Walk achievement unlock
        boolean walkAchievementUnlockedComplete = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("walk_achievement_unlocked_complete",false); //Triggered by pressing the end session button in ActiveSessionActivity.java
        boolean hourSessionAchievement = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("hour_session_achievement", false); //1+ hour session achievement
        boolean fourteenDayAchievement = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("fourteen_day_achievement", false); //14-day streak achievement
        boolean runAchievementUnlocked = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("walk_achievement_unlocked",false); //Run achievement unlock
        boolean runAchievementUnlockedComplete = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("run_achievement_unlocked_complete",false); //Triggered by pressing the end session button in ActiveSessionActivity.java
        boolean powerWalkAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("power_walk_achievement_unlocked", false);
        boolean powerWalkAchievementComplete = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("power_walk_achievement_unlocked_complete", false);
        boolean twentyOneDayAchievement = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("twentyone_day_achievement", false); //21-day streak achievement
        boolean fiftyDayAchievement = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("fifty_day_achievement", false); //50-day streak achievement
        boolean oneHundredDayAchievement = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("onehundred_day_achievement", false); //100-day streak achievement

        // Update achievement statuses to be red or green based on if they're locked or unlocked
        updateAchievementStatus(R.id.achievement1, "Walk with the app for the first time!", walkAchievementUnlocked && walkAchievementUnlockedComplete);
        updateAchievementStatus(R.id.achievement2, "Exercise for 1 hour in a single session!", hourSessionAchievement);
        updateAchievementStatus(R.id.achievement3, "Exercise for 14 days in a row!", fourteenDayAchievement);
        updateAchievementStatus(R.id.achievement4, "Run with the app for the first time!", runAchievementUnlocked && runAchievementUnlockedComplete);
        updateAchievementStatus(R.id.achievement5, "Power Walk with the app for the first time!", powerWalkAchievementUnlocked && powerWalkAchievementComplete);
        updateAchievementStatus(R.id.achievement6, "Exercise for 21 days in a row!", twentyOneDayAchievement);
        updateAchievementStatus(R.id.achievement7, "Exercise for 50 days in a row!", fiftyDayAchievement);
        updateAchievementStatus(R.id.achievement8, "Exercise for 100 days in a row!", oneHundredDayAchievement);


        //Checks for Walk for the first time achievement
        if (walkAchievementUnlocked && walkAchievementUnlockedComplete){
            LinearLayout achievement1 = findViewById(R.id.achievement1);
            TextView achievementText = (TextView) achievement1.getChildAt(0);
            achievementText.setText("Walk with the app for the first time!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement1, "Walk with the app for the first time!", walkAchievementUnlocked && walkAchievementUnlockedComplete);
        }
        //Checks for 1+ hour session achievement
        if (hourSessionAchievement) {
            LinearLayout achievement2 = findViewById(R.id.achievement2);
            TextView achievementText = (TextView) achievement2.getChildAt(0);
            achievementText.setText("Exercise for 1 hour in a single session!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement2, "Exercise for 1 hour in a single session!", hourSessionAchievement);
        }

        //Checks for 14 day streak achievement
        if (fourteenDayAchievement) {
            LinearLayout achievement3 = findViewById(R.id.achievement3);
            TextView achievementText = (TextView) achievement3.getChildAt(0);
            achievementText.setText("Exercise for 14 days in a row!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement3, "Exercise for 14 days in a row!", fourteenDayAchievement);
        }

        //Checks for Run for the first time achievement
        if (runAchievementUnlocked && runAchievementUnlockedComplete) {
            LinearLayout achievement4 = findViewById(R.id.achievement4);
            TextView achievementText = (TextView) achievement4.getChildAt(0);
            achievementText.setText("Run with the app for the first time!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement4, "Run with the app for the first time!", runAchievementUnlocked && runAchievementUnlockedComplete);
        }

        //Checks for Power Walk for the first time achievement
        if (powerWalkAchievementUnlocked && powerWalkAchievementComplete) {
            LinearLayout achievement5 = findViewById(R.id.achievement5);
            TextView achievementText = (TextView) achievement5.getChildAt(0);
            achievementText.setText("Power Walk with the app for the first time!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement5, "Power Walk with the app for the first time!", powerWalkAchievementUnlocked && powerWalkAchievementComplete);
        }

        //Checks for 21 day streak achievement
        if (twentyOneDayAchievement) {
            LinearLayout achievement6 = findViewById(R.id.achievement6);
            TextView achievementText = (TextView) achievement6.getChildAt(0);
            achievementText.setText("Exercise for 21 days in a row!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement6, "Exercise for 21 days in a row!", twentyOneDayAchievement);
        }

        //Checks for 50 day streak achievement
        if (fiftyDayAchievement) {
            LinearLayout achievement7 = findViewById(R.id.achievement7);
            TextView achievementText = (TextView) achievement7.getChildAt(0);
            achievementText.setText("Exercise for 50 days in a row!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement7, "Exercise for 50 days in a row!", fiftyDayAchievement);
        }

        //Checks for 50 day streak achievement
        if (oneHundredDayAchievement) {
            LinearLayout achievement8 = findViewById(R.id.achievement8);
            TextView achievementText = (TextView) achievement8.getChildAt(0);
            achievementText.setText("Exercise for 100 days in a row!\nStatus: Unlocked!");
            updateAchievementStatus(R.id.achievement8, "Exercise for 100 days in a row!", oneHundredDayAchievement);
        }
    }

    private void updateAchievementStatus(int layoutId, String baseText, boolean isUnlocked) {
        LinearLayout achievementLayout = findViewById(layoutId);
        TextView achievementText = (TextView) achievementLayout.getChildAt(0);

        String statusText = isUnlocked ? "Unlocked" : "Locked";
        int color = isUnlocked
                ? getResources().getColor(android.R.color.holo_green_dark)
                : getResources().getColor(android.R.color.holo_red_dark);

        // Create a SpannableString
        String fullText = baseText + "\nStatus: " + statusText;
        SpannableString spannable = new SpannableString(fullText);

        // Apply color to the word "Locked" or "Unlocked"
        int startIndex = fullText.indexOf(statusText);
        int endIndex = startIndex + statusText.length();
        spannable.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to the TextView
        achievementText.setText(spannable);
    }
}
