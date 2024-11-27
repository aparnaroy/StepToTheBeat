package com.example.steptothebeat;

import android.os.Bundle;

public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        addMenuBarSpace(R.id.profile);

        // Set up toolbar
        setupToolbar(R.id.toolbar, true);
    }
}
