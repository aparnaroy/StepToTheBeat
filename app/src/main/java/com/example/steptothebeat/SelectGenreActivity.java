package com.example.steptothebeat;

import android.os.Bundle;
import android.widget.TextView;

public class SelectGenreActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre_activity);
        addMenuBarSpace(R.id.select_genre);
        setupToolbar(R.id.menubar, true);

//        initializeButtons();

        // Get the activity type passed from the ChoosePaceActivity
        String pace = getIntent().getStringExtra("pace");

        // Display the activity type in a TextView (just for example)
        TextView selectedActivityTextView = findViewById(R.id.selected_pace_text);
        if (pace != null) {
            selectedActivityTextView.setText("Selected Pace: " + pace);
        }
    }

}
