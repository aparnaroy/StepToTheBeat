package com.example.steptothebeat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

public class ActiveSessionActivity extends BaseActivity {

    private ImageButton endSessionButton;
    private TextView currentTrackTextView, currentArtistTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_session_activity); // Set the layout to activity_home.xml
        addMenuBarSpace(R.id.active_session);

        // Initialize the TextViews
        currentTrackTextView = findViewById(R.id.current_track);  // Make sure the IDs match
        currentArtistTextView = findViewById(R.id.current_artist);

        // Get the track and artist from the Intent
        String trackName = getIntent().getStringExtra("track");
        String artistName = getIntent().getStringExtra("artist");


        // Set up toolbar
        setupToolbar(R.id.menubar, true);

        EdgeToEdge.enable(this);

        // Get the activity type passed from the ChoosePaceActivity
        String pace = getIntent().getStringExtra("pace");
        // Display the activity type in a TextView (just for example)
        TextView selectedActivityTextView = findViewById(R.id.exercise);
        if (pace != null) {
            selectedActivityTextView.setText("Exercise: " + pace);
        }

        // Initialize views
        if (trackName != null && artistName != null) {
            currentTrackTextView.setText("Now Playing: " + trackName);
            currentArtistTextView.setText("Artist: " + artistName);
        }
        else{
            currentTrackTextView.setText("Now Playing: No track selected");
            currentArtistTextView.setText("Artist: No artist selected");
        }
        init();
    }

    @SuppressLint("NewApi")
    void init() {
        // Initialize the buttons
        endSessionButton = findViewById(R.id.end_session);
//        pauseButton = findViewById(R.id.pause_button);

        // Navigate to correct page on button click
        endSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Session Summary page
                Intent intent = new Intent(ActiveSessionActivity.this, SessionSummaryActivity.class);
                startActivity(intent);
            }
        });

//        pauseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Go to Settings page
//                Intent intent = new Intent(ActiveSessionActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    private void updateNowPlaying(String trackName, String artistName) {
        currentTrackTextView.setText("Now Playing: " + trackName);
        currentArtistTextView.setText("Artist: " + artistName);
    }
}
