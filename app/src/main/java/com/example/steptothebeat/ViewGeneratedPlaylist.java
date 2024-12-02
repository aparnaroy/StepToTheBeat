package com.example.steptothebeat;

import android.os.Bundle;
import android.widget.TextView;

public class ViewGeneratedPlaylist extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_generated_playlist);
        addMenuBarSpace(R.id.view_playlist);
        setupToolbar(R.id.menubar, true);

        String playlist = getIntent().getStringExtra("playlist");

        TextView selectedActivityTextView = findViewById(R.id.selected_playlist_text);
        if (playlist != null) {
            selectedActivityTextView.setText("Selected: " + playlist);
        }

    }
}
