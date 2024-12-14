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
        if (walkAchievementUnlocked && walkAchievementUnlockedComplete){
            LinearLayout achievement1 = findViewById(R.id.achievement1);
            TextView achievementText = (TextView) achievement1.getChildAt(0);
            achievementText.setText("Walk with the app for the first time!\nStatus: Unlocked!");
        }
    }

}
