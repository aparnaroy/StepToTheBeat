package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewGeneratedPlaylist extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_generated_playlist);
        addMenuBarSpace(R.id.view_playlist);
        setupToolbar(R.id.menubar, true);

        String playlist = getIntent().getStringExtra("playlist");

        // Buttons
        LinearLayout startRun = findViewById(R.id.start_run);

        // Set Listeners
        startRun.setOnClickListener(view -> startRun());

        TextView selectedActivityTextView = findViewById(R.id.selected_playlist_text);
        if (playlist != null) {
            selectedActivityTextView.setText(playlist);
        }

    }

    public void startRun() {
        Intent intent = new Intent(this, ActiveSessionActivity.class);
        startActivity(intent);
    }

}
