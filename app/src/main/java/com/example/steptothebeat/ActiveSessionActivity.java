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
    private TextView currentTrackTextView, currentArtistTextView, currentPlaylistTextView;


    private BroadcastReceiver songUpdateReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the updated track and artist info from the Intent
            String trackName = intent.getStringExtra("track");
            String artistName = intent.getStringExtra("artist");
            String playlistName = intent.getStringExtra("playlist");

            // Update the UI with the new track info
            if (trackName != null && artistName != null) {
                currentTrackTextView.setText("Now Playing: " + trackName);
                currentArtistTextView.setText("Artist: " + artistName);
                currentPlaylistTextView.setText("Playlist: " + playlistName);
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_session_activity); // Set the layout to activity_home.xml
        addMenuBarSpace(R.id.active_session);

        // Initialize the TextViews
        currentTrackTextView = findViewById(R.id.current_track);
        currentArtistTextView = findViewById(R.id.current_artist);
        currentPlaylistTextView = findViewById(R.id.current_playlist);

//        // Get the track and artist from the Intent
        String trackName = getIntent().getStringExtra("track");
        String artistName = getIntent().getStringExtra("artist");
        String playlistName = getIntent().getStringExtra("playlist");

//

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

         //Initialize views
        if (trackName != null && artistName != null) {
            currentTrackTextView.setText("Now Playing: " + trackName);
            currentArtistTextView.setText("Artist: " + artistName);
            currentPlaylistTextView.setText("Playlist: " + getIntent().getStringExtra("playlist"));
        }
        else{
            currentTrackTextView.setText("Now Playing: No track selected");
            currentArtistTextView.setText("Artist: No artist selected");
            currentPlaylistTextView.setText("Playlist: No playlist selected");
        }
        //initialize the session
        init();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
        // Register the BroadcastReceiver to listen for updates when the activity becomes visible
        IntentFilter filter = new IntentFilter("com.example.steptothebeat.SONG_UPDATE");
        registerReceiver(songUpdateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get the initial track data from the Intent and set it when the activity comes into focus
        String trackName = getIntent().getStringExtra("track");
        String artistName = getIntent().getStringExtra("artist");
        String playlistName = getIntent().getStringExtra("playlist");

        if (trackName != null && artistName != null) {
            currentTrackTextView.setText("Now Playing: " + trackName);
            currentArtistTextView.setText("Artist: " + artistName);
            currentPlaylistTextView.setText("Playlist: " + playlistName);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unregister the BroadcastReceiver when the activity is destroyed to avoid memory leaks
        unregisterReceiver(songUpdateReceiver);
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

    }

    private void updateNowPlaying(String trackName, String artistName) {
        currentTrackTextView.setText("Now Playing: " + trackName);
        currentArtistTextView.setText("Artist: " + artistName);
    }
}



