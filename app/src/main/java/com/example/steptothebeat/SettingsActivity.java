package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        addMenuBarSpace(R.id.settings);
        setupToolbar(R.id.toolbar, true);

        initializeButtons();
    }

    void initializeButtons() {
        // Setup the button clicks
        Button accountInfoButton = findViewById(R.id.account_information);
        Button profileButton = findViewById(R.id.profile);
        Button runningHistoryButton = findViewById(R.id.running_history);
        Button calibrationButton = findViewById(R.id.calibration);
        Button accountInfoArrowButton = findViewById(R.id.account_info_arrow);
        Button profileArrowButton = findViewById(R.id.profile_arrow);
        Button runningHistoryArrowButton = findViewById(R.id.running_history_arrow);
        Button calibrationArrowButton = findViewById(R.id.calibration_arrow);

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

        profileArrowButton.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

//        runningHistoryButton.setOnClickListener(v -> {
//            // Navigate to Running History Activity
//            Intent intent = new Intent(SettingsActivity.this, RunningHistoryActivity.class);
//            startActivity(intent);
//        });
//
//        calibrationButton.setOnClickListener(v -> {
//            // Navigate to Calibration Activity
//            Intent intent = new Intent(SettingsActivity.this, CalibrationActivity.class);
//            startActivity(intent);
//        });
    }
}
