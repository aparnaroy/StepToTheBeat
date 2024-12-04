package com.example.steptothebeat;

import android.os.Bundle;
import android.util.Log;


public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ProfileActivity", "inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        addMenuBarSpace(R.id.profile);

        // Set up toolbar
        setupToolbar(R.id.menubar, true);
    }
}


