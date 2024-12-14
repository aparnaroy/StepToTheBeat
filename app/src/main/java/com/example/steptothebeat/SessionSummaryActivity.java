package com.example.steptothebeat;


import android.os.Bundle;

public class SessionSummaryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_session);
        addMenuBarSpace(R.id.session_summary);

        // Set up toolbar
        setupToolbar(R.id.menubar, true);
    }
}
