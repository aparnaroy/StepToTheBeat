package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        addMenuBarSpace(R.id.settings);
        setupToolbar(R.id.menubar, true);

        initializeButtons();
    }

    void initializeButtons() {
        // Set up the button clicks
        Button accountInfoButton = findViewById(R.id.account_information);
        Button profileButton = findViewById(R.id.profile);
        Button runningHistoryButton = findViewById(R.id.running_history);
        Button calibrationButton = findViewById(R.id.calibration);

//        accountInfoButton.setOnClickListener(v -> {
//            // Navigate to Account Information Activity
//            Intent intent = new Intent(SettingsActivity.this, AccountInformationActivity.class);
//            startActivity(intent);
//        });

        profileButton.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

//        runningHistoryButton.setOnClickListener(v -> {
//            // Navigate to Running History Activity
//            Intent intent = new Intent(SettingsActivity.this, RunningHistoryActivity.class);
//            startActivity(intent);
//        });

        calibrationButton.setOnClickListener(v -> {
            // Navigate to Calibration Activity
            Intent intent = new Intent(SettingsActivity.this, WalkCalibrationActivity.class);
            startActivity(intent);
        });
    }
}
