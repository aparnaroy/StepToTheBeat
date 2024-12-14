package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
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
        boolean runAchievementUnlocked = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("walk_achievement_unlocked",false);
        boolean runAchievementUnlockedComplete = getSharedPreferences("achievements",MODE_PRIVATE).getBoolean("run_achievement_unlocked_complete",false); //Triggered by pressing the end session button in ActiveSessionActivity.java
        boolean powerWalkAchievementUnlocked = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("power_walk_achievement_unlocked", false);
        boolean powerWalkAchievementComplete = getSharedPreferences("achievements", MODE_PRIVATE).getBoolean("power_walk_achievement_unlocked_complete", false);

        //Checks for Walk for the first time achievement
        if (walkAchievementUnlocked && walkAchievementUnlockedComplete){
            LinearLayout achievement1 = findViewById(R.id.achievement1);
            TextView achievementText = (TextView) achievement1.getChildAt(0);
            achievementText.setText("Walk with the app for the first time!\nStatus: Unlocked!");
        }
        //Checks for 1+ hour session achievement
        if (hourSessionAchievement) {
            LinearLayout achievement2 = findViewById(R.id.achievement2);
            TextView achievementText = (TextView) achievement2.getChildAt(0);
            achievementText.setText("Exercise for 1 hour in a single session!\nStatus: Unlocked!");
        }
        if (fourteenDayAchievement) {
            LinearLayout achievement3 = findViewById(R.id.achievement3);
            TextView achievementText = (TextView) achievement3.getChildAt(0);
            achievementText.setText("Exercise for 14 days in a row!\nStatus: Unlocked!");
        }
        if (runAchievementUnlocked && runAchievementUnlockedComplete) {
            LinearLayout achievement4 = findViewById(R.id.achievement4);
            TextView achievementText = (TextView) achievement4.getChildAt(0);
            achievementText.setText("Run with the app for the first time!\nStatus: Unlocked!");
        }
        if (powerWalkAchievementUnlocked && powerWalkAchievementComplete) {
            LinearLayout achievement5 = findViewById(R.id.achievement5);
            TextView achievementText = (TextView) achievement5.getChildAt(0);
            achievementText.setText("Power Walk with the app for the first time!\nStatus: Unlocked!");
        }

    }

}
