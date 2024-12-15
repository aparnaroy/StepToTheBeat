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
import android.os.Handler;
import android.os.Looper;
import androidx.activity.EdgeToEdge;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.content.SharedPreferences;

public class ActiveSessionActivity extends BaseActivity {

    private ImageButton endSessionButton;
    private TextView currentTrackTextView, currentArtistTextView, tempoTextView, timerTextView;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private long startTime = 0L;
    private long elapsedTime = 0L;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            elapsedTime = millis;

            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            timerHandler.postDelayed(this, 1000);
        }
    };

    private void updateConsecutiveDays() {
        SharedPreferences prefs = getSharedPreferences("achievements", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Get today's date and format it
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Get the last exercise date
        String lastDate = prefs.getString("last_exercise_date", "");

        if (!lastDate.equals(today)) {  // Only update if not already logged today
            // Get current streak
            int currentStreak = prefs.getInt("exercise_streak", 0);

            if (isConsecutiveDay(lastDate)) {
                currentStreak++;
            } else {
                currentStreak = 1; // Reset streak if not consecutive
            }

            // Save new streak and date
            editor.putInt("exercise_streak", currentStreak);
            editor.putString("last_exercise_date", today);

            // Check if 14-day achievement unlocked
            if (currentStreak >= 14) {
                editor.putBoolean("fourteen_day_achievement", true);
            }

            editor.apply();
        }
    }

    private boolean isConsecutiveDay(String lastDate) {
        if (lastDate.isEmpty()) return true;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date last = sdf.parse(lastDate);
            Date today = new Date();

            // Calculate difference in days
            long diffInMillies = today.getTime() - last.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diffInDays == 1;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_session_activity); // Set the layout to activity_home.xml
        addMenuBarSpace(R.id.active_session);

        // Initialize the TextViews
        currentTrackTextView = findViewById(R.id.current_track);  // Make sure the IDs match
        currentArtistTextView = findViewById(R.id.current_artist);
        timerTextView = findViewById(R.id.timer_text);
        tempoTextView = findViewById(R.id.tempo);

        tempoTextView.setText("120 BPM"); // TODO: CHANGE THIS TO BE THE SELECTED PACE BPM

        //Start the timer
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

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

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (startTime > 0) {
            timerHandler.postDelayed(timerRunnable, 0);
        }
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
                // Save elapsed time to SharedPreferences
                getSharedPreferences("session", MODE_PRIVATE)
                        .edit()
                        .putLong("session_duration", elapsedTime)
                        .apply();

                //Update consecutive days
                updateConsecutiveDays();

                // Go to Session Summary page
                getSharedPreferences("achievements", MODE_PRIVATE).edit().putBoolean("walk_achievement_unlocked_complete",true).apply();

                // Set power walk achievement complete
                getSharedPreferences("achievements", MODE_PRIVATE).edit().putBoolean("power_walk_achievement_unlocked_complete",true).apply();
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
