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

//        walkButton.setOnClickListener(v -> {
//            // Navigate to Start Activity Activity
//            Intent intent = new Intent(ChoosePaceActivity.this, StartWorkoutActivity.class);
//            startActivity(intent);
//        });
        walkButton.setOnClickListener(v -> {
            // Navigate to Start Activity Activity
            Intent intent = new Intent(ChoosePaceActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
