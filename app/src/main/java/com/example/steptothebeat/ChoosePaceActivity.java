package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ChoosePaceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_pace_activity);
        addMenuBarSpace(R.id.choose_pace);
        setupToolbar(R.id.menubar, true);

        initializeButtons();
    }

    void initializeButtons() {
        LinearLayout walkButton = findViewById(R.id.walk_button);
        LinearLayout powerWalkButton = findViewById(R.id.powerwalk_button);
        LinearLayout jogButton = findViewById(R.id.jog_button);
        LinearLayout runButton = findViewById(R.id.run_button);
        LinearLayout sprintButton = findViewById(R.id.sprint_button);

        // Walk Button
        walkButton.setOnClickListener(v -> {
            getSharedPreferences("achievements", MODE_PRIVATE).edit().putBoolean("walk_achievement_unlocked",true).apply();
            Intent intent = new Intent(ChoosePaceActivity.this, SelectGenreActivity.class);
            intent.putExtra("pace", "Walk");
            startActivity(intent);
        });

        // Power Walk Button
        powerWalkButton.setOnClickListener(v -> {
            getSharedPreferences("achievements", MODE_PRIVATE).edit().putBoolean("power_walk_achievement_unlocked",true).apply();
            Intent intent = new Intent(ChoosePaceActivity.this, SelectGenreActivity.class);
            intent.putExtra("pace", "Power Walk"); // Passing data
            startActivity(intent);
        });

        // Jog Button
        jogButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChoosePaceActivity.this, SelectGenreActivity.class);
            intent.putExtra("pace", "Jog"); // Passing data
            startActivity(intent);
        });

        // Run Button
        runButton.setOnClickListener(v -> {
            getSharedPreferences("achievements", MODE_PRIVATE).edit().putBoolean("run_achievement_unlocked",true).apply();
            Intent intent = new Intent(ChoosePaceActivity.this, SelectGenreActivity.class);
            intent.putExtra("pace", "Run"); // Passing data
            startActivity(intent);
        });

        // Sprint Button
        sprintButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChoosePaceActivity.this, SelectGenreActivity.class);
            intent.putExtra("pace", "Sprint"); // Passing data
            startActivity(intent);
        });
    }
}
